package com.micro.flight_ops_service.mapper;

import com.micro.enums.FlightStatus;
import com.micro.payload.request.FlightInstanceRequest;
import com.micro.payload.response.AirportResponse;
import com.micro.payload.response.FlightInstanceResponse;
import com.micro.flight_ops_service.model.Flight;
import com.micro.flight_ops_service.model.FlightInstance;

public class FlightInstanceMapper {

    public static FlightInstance toEntity(FlightInstanceRequest request, Flight flight) {
        if (request == null) {
            return null;
        }

        return FlightInstance.builder()
                .flight(flight)
                .airlineId(flight != null ? flight.getAirlineId() : null)
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .scheduleId(request.getScheduleId())
                .departureDateTime(request.getDepartureDateTime())
                .arrivalDateTime(request.getArrivalDateTime())
                .totalSeats(request.getTotalSeats())
                .availableSeats(request.getAvailableSeats())
                .status(request.getStatus())
                .minAdvanceBookingDays(request.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(request.getMaxAdvanceBookingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightInstanceResponse toResponse(FlightInstance entity) {
        if (entity == null) {
            return null;
        }

        AirportResponse departureAirport = entity.getDepartureAirportId() != null
                ? AirportResponse.builder().id(entity.getDepartureAirportId()).build()
                : null;

        AirportResponse arrivalAirport = entity.getArrivalAirportId() != null
                ? AirportResponse.builder().id(entity.getArrivalAirportId()).build()
                : null;

        return FlightInstanceResponse.builder()
                .id(entity.getId())
                .flightId(entity.getFlight() != null ? entity.getFlight().getId() : null)
                .flightNumber(entity.getFlight() != null ? entity.getFlight().getFlightNumber() : null)
                .airlineId(entity.getAirlineId())
                .airlineName(null)
                .airlineLogo(null)
                .aircraftId(entity.getFlight() != null ? entity.getFlight().getAircraftId() : null)
                .aircraftModel(null)
                .aircraftCode(null)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureDateTime(entity.getDepartureDateTime())
                .arrivalDateTime(entity.getArrivalDateTime())
                .formattedDuration(entity.getFormatedDuration())
                .totalSeats(entity.getTotalSeats())
                .availableSeats(entity.getAvailableSeats())
                .status(entity.getStatus())
                .minAdvanceBookingDays(entity.getMinAdvanceBookingDays())
                .maxAdvanceBookingDays(entity.getMaxAdvanceBookingDays())
                .isActive(entity.getIsActive())
                .build();
    }
}
