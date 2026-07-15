package com.micro.flight_ops_service.mapper;

import com.micro.payload.response.AircraftResponse;
import com.micro.payload.response.AirlineResponse;
import com.micro.payload.response.AirportResponse;
import com.micro.payload.response.FlightResponse;
import com.micro.flight_ops_service.model.Flight;

public class FlightMapper {

    public static FlightResponse toResponse(
            Flight flight,
            AircraftResponse aircraft,
            AirlineResponse airline,
            AirportResponse departureAirport,
            AirportResponse arrivalAirport) {

        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .aircraft(aircraft)
                .airline(airline)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }
}
