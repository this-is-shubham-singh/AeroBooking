package com.micro.flight_ops_service.service;

import com.micro.enums.FlightStatus;
import com.micro.payload.request.FlightRequest;
import com.micro.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    FlightResponse createFlight(Long airlineId, FlightRequest flightRequest);

    Page<FlightResponse> getFlightsByAirline(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Pageable pageable);

    FlightResponse getFlightById(Long id);

    FlightResponse updateFlight(Long id, FlightRequest flightRequest);

    FlightResponse changeStatus(Long id, FlightStatus status);

    void deleteFlight(Long airlineId, Long id);
}
