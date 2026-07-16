package com.micro.flight_ops_service.service.Impl;

import com.micro.payload.request.FlightInstanceRequest;
import com.micro.payload.response.AircraftResponse;
import com.micro.payload.response.FlightInstanceResponse;
import com.micro.flight_ops_service.model.Flight;
import com.micro.flight_ops_service.model.FlightInstance;
import com.micro.flight_ops_service.repository.FlightInstanceRepository;
import com.micro.flight_ops_service.repository.FlightRepository;
import com.micro.flight_ops_service.service.FlightInstanceService;
import com.micro.flight_ops_service.mapper.FlightInstanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FlightInstanceServiceImpl implements FlightInstanceService {

    private final FlightInstanceRepository flightInstanceRepository;
    private final FlightRepository flightRepository;

    @Override
    public FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request) {
        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight Not Found!"));

        // Dummy aircraft logic from instructor's notes
        AircraftResponse aircraftResponse = AircraftResponse.builder()
                .id(1L)
                .totalSeats(90)
                .build();

        FlightInstance flightInstance = FlightInstanceMapper.toEntity(request, flight);
        flightInstance.setTotalSeats(aircraftResponse.getTotalSeats());
        flightInstance.setAvailableSeats(aircraftResponse.getTotalSeats());

        FlightInstance saved = flightInstanceRepository.save(flightInstance);
        return FlightInstanceMapper.toResponse(saved);
    }

    @Override
    public FlightInstanceResponse getFlightInstanceById(Long id) {
        FlightInstance instance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight Instance not found with ID: " + id));
        return FlightInstanceMapper.toResponse(instance);
    }

    @Override
    public Page<FlightInstanceResponse> getByAirlineId(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            Long onDate,
            Pageable pageable) {

        LocalDateTime dayStart = null;
        LocalDateTime dayEnd = null;

        if (onDate != null) {
            java.time.LocalDate localDate = java.time.Instant.ofEpochMilli(onDate)
                    .atZone(java.time.ZoneId.systemDefault())
                    .toLocalDate();
            dayStart = localDate.atStartOfDay();
            dayEnd = localDate.atTime(java.time.LocalTime.MAX);
        }

        return flightInstanceRepository.findByFilters(
                airlineId,
                departureAirportId,
                arrivalAirportId,
                flightId,
                dayStart,
                dayEnd,
                pageable).map(FlightInstanceMapper::toResponse);
    }

    @Override
    public FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request) {
        FlightInstance instance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight Instance not found with ID: " + id));

        if (request.getFlightId() != null && !instance.getFlight().getId().equals(request.getFlightId())) {
            Flight flight = flightRepository.findById(request.getFlightId())
                    .orElseThrow(() -> new RuntimeException("Flight Not Found!"));
            instance.setFlight(flight);
        }

        if (request.getDepartureAirportId() != null) {
            instance.setDepartureAirportId(request.getDepartureAirportId());
        }
        if (request.getArrivalAirportId() != null) {
            instance.setArrivalAirportId(request.getArrivalAirportId());
        }
        if (request.getScheduleId() != null) {
            instance.setScheduleId(request.getScheduleId());
        }
        if (request.getDepartureDateTime() != null) {
            instance.setDepartureDateTime(request.getDepartureDateTime());
        }
        if (request.getArrivalDateTime() != null) {
            instance.setArrivalDateTime(request.getArrivalDateTime());
        }
        if (request.getTotalSeats() != null) {
            instance.setTotalSeats(request.getTotalSeats());
        }
        if (request.getAvailableSeats() != null) {
            instance.setAvailableSeats(request.getAvailableSeats());
        }
        if (request.getStatus() != null) {
            instance.setStatus(request.getStatus());
        }
        if (request.getMinAdvanceBookingDays() != null) {
            instance.setMinAdvanceBookingDays(request.getMinAdvanceBookingDays());
        }
        if (request.getMaxAdvanceBookingDays() != null) {
            instance.setMaxAdvanceBookingDays(request.getMaxAdvanceBookingDays());
        }
        if (request.getIsActive() != null) {
            instance.setIsActive(request.getIsActive());
        }

        FlightInstance updated = flightInstanceRepository.save(instance);
        return FlightInstanceMapper.toResponse(updated);
    }

    @Override
    public void deleteFlightInstance(Long airlineId, Long id) {
        FlightInstance instance = flightInstanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight Instance not found with ID: " + id));

        if (airlineId != null && !instance.getAirlineId().equals(airlineId)) {
            throw new RuntimeException("Flight instance does not belong to the specified airline ID: " + airlineId);
        }

        flightInstanceRepository.delete(instance);
    }
}
