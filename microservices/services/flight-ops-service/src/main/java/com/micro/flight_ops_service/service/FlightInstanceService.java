package com.micro.flight_ops_service.service;

import com.micro.payload.request.FlightInstanceRequest;
import com.micro.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request);

    FlightInstanceResponse getFlightInstanceById(Long id);

    Page<FlightInstanceResponse> getByAirlineId(
            Long airlineId,
            Long departureAirportId,
            Long arrivalAirportId,
            Long flightId,
            Long onDate,
            Pageable pageable
    );

    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request);

    void deleteFlightInstance(Long id);
}
