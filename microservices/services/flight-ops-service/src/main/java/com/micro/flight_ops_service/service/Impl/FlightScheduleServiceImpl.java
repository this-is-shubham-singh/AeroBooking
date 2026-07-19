package com.micro.flight_ops_service.service.Impl;

import com.micro.enums.FlightStatus;
import com.micro.payload.request.FlightInstanceRequest;
import com.micro.payload.request.FlightScheduleRequest;
import com.micro.payload.response.FlightScheduleResponse;
import com.micro.flight_ops_service.model.Flight;
import com.micro.flight_ops_service.model.FlightSchedule;
import com.micro.flight_ops_service.repository.FlightRepository;
import com.micro.flight_ops_service.repository.FlightScheduleRepository;
import com.micro.flight_ops_service.service.FlightInstanceService;
import com.micro.flight_ops_service.service.FlightScheduleService;
import com.micro.flight_ops_service.mapper.FlightScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {

    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightInstanceService flightInstanceService;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start date cannot be after end date!");
        }

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight Not Found!"));

        FlightSchedule flightSchedule = FlightScheduleMapper.toEntity(request, flight);
        FlightSchedule savedSchedule = flightScheduleRepository.save(flightSchedule);

        List<DayOfWeek> operatingDays = savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        // Overnight arrival date logic check
        boolean isOvernight = savedSchedule.getArrivalTime().isBefore(savedSchedule.getDepartureTime());

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .scheduleId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .totalSeats(90) // dummy seat capacity to pass DTO validation
                .status(FlightStatus.SCHEDULED)
                .build();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (operatingDays != null && operatingDays.contains(date.getDayOfWeek())) {

                LocalDate arrivalDate = isOvernight ? date.plusDays(1) : date;

                flightInstanceRequest.setDepartureDateTime(LocalDateTime.of(date, savedSchedule.getDepartureTime()));
                flightInstanceRequest.setArrivalDateTime(LocalDateTime.of(arrivalDate, savedSchedule.getArrivalTime()));

                // User ID parameter mapped as dummy 1L for standalone flow
                flightInstanceService.createFlightInstance(1L, flightInstanceRequest);
            }
        }

        return FlightScheduleMapper.toResponse(savedSchedule);
    }

    @Override
    public FlightScheduleResponse getFlightScheduleById(Long id) {
        FlightSchedule schedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("flight schedule not found with id: " + id));
        return FlightScheduleMapper.toResponse(schedule);
    }

    @Override
    public List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId) {
        // Bypassing user-service call: treating userId as airlineId directly
        List<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(userId);
        return schedules.stream()
                .map(FlightScheduleMapper::toResponse)
                .toList();
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("flight schedule not found with id: " + id));

        FlightScheduleMapper.updateEntity(flightScheduleRequest, flightSchedule);
        FlightSchedule updatedSchedule = flightScheduleRepository.save(flightSchedule);
        return FlightScheduleMapper.toResponse(updatedSchedule);
    }

    @Override
    public void deleteFlightSchedule(Long id) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("flight schedule not found with id: " + id));
        flightScheduleRepository.delete(flightSchedule);
    }
}
