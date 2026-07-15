package com.micro.flight_ops_service.service.Impl;

import com.micro.enums.FlightStatus;
import com.micro.payload.request.FlightRequest;
import com.micro.payload.response.AircraftResponse;
import com.micro.payload.response.AirlineResponse;
import com.micro.payload.response.AirportResponse;
import com.micro.payload.response.FlightResponse;
import com.micro.flight_ops_service.model.Flight;
import com.micro.flight_ops_service.repository.FlightRepository;
import com.micro.flight_ops_service.service.FlightService;
import com.micro.flight_ops_service.mapper.FlightMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest flightRequest) {
        if (flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new RuntimeException("Flight number already exists: " + flightRequest.getFlightNumber());
        }

        Flight flight = Flight.builder()
                .flightNumber(flightRequest.getFlightNumber())
                .airlineId(airlineId)
                .aircraftId(flightRequest.getAircraftId())
                .departureAirportId(flightRequest.getDepartureAirportId())
                .arrivalAirportId(flightRequest.getArrivalAirportId())
                .status(flightRequest.getStatus())
                .build();

        Flight savedFlight = flightRepository.save(flight);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public Page<FlightResponse> getFlightsByAirline(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Pageable pageable) {

        return flightRepository.findByAirlineId(airlineId, departureAirportId, arrivalAirportId, pageable)
                .map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));
        return convertToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));

        if (flightRequest.getFlightNumber() != null && !flight.getFlightNumber().equals(flightRequest.getFlightNumber())
                && flightRepository.existsByFlightNumber(flightRequest.getFlightNumber())) {
            throw new RuntimeException("Flight number already exists: " + flightRequest.getFlightNumber());
        }

        // Safe null-checks for partial updates
        if (flightRequest.getFlightNumber() != null) {
            flight.setFlightNumber(flightRequest.getFlightNumber());
        }
        if (flightRequest.getAirlineId() != null) {
            flight.setAirlineId(flightRequest.getAirlineId());
        }
        if (flightRequest.getAircraftId() != null) {
            flight.setAircraftId(flightRequest.getAircraftId());
        }
        if (flightRequest.getDepartureAirportId() != null) {
            flight.setDepartureAirportId(flightRequest.getDepartureAirportId());
        }
        if (flightRequest.getArrivalAirportId() != null) {
            flight.setArrivalAirportId(flightRequest.getArrivalAirportId());
        }
        if (flightRequest.getStatus() != null) {
            flight.setStatus(flightRequest.getStatus());
        }

        Flight updatedFlight = flightRepository.save(flight);
        return convertToFlightResponse(updatedFlight);
    }

    @Override
    public FlightResponse changeStatus(Long id, FlightStatus status) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));

        flight.setStatus(status);
        Flight updatedFlight = flightRepository.save(flight);
        return convertToFlightResponse(updatedFlight);
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with ID: " + id));
        if (!flight.getAirlineId().equals(airlineId)) {
            throw new RuntimeException("Flight does not belong to the specified airline ID: " + airlineId);
        }
        flightRepository.delete(flight);
    }

    private FlightResponse convertToFlightResponse(Flight flight) {
        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();

        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return FlightMapper.toResponse(flight, aircraft, airline, departureAirport, arrivalAirport);
    }
}
