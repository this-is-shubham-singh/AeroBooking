package com.micro.flight_ops_service.mapper;

import com.micro.payload.request.FlightScheduleRequest;
import com.micro.payload.response.AirportResponse;
import com.micro.payload.response.FlightScheduleResponse;
import com.micro.flight_ops_service.model.Flight;
import com.micro.flight_ops_service.model.FlightSchedule;

public class FlightScheduleMapper {

    public static FlightSchedule toEntity(FlightScheduleRequest request, Flight flight) {
        if (request == null) {
            return null;
        }

        return FlightSchedule.builder()
                .flight(flight)
                .departureAirportId(flight.getDepartureAirportId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .operatingDays(request.getOperatingDays())
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();
    }

    public static FlightScheduleResponse toResponse(FlightSchedule entity) {
        if (entity == null) {
            return null;
        }

        AirportResponse departureAirport = entity.getDepartureAirportId() != null
                ? AirportResponse.builder().id(entity.getDepartureAirportId()).build()
                : null;

        AirportResponse arrivalAirport = entity.getArrivalAirportId() != null
                ? AirportResponse.builder().id(entity.getArrivalAirportId()).build()
                : null;

        return FlightScheduleResponse.builder()
                .id(entity.getId())
                .flightId(entity.getFlight() != null ? entity.getFlight().getId() : null)
                .flightNumber(entity.getFlight() != null ? entity.getFlight().getFlightNumber() : null)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .departureTime(entity.getDepartureTime())
                .arrivalTime(entity.getArrivalTime())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .operatingDays(entity.getOperatingDays())
                .isActive(entity.isActive())
                .build();
    }

    public static void updateEntity(FlightScheduleRequest request, FlightSchedule entity) {
        if (request == null || entity == null) {
            return;
        }
        if (request.getDepartureTime() != null) {
            entity.setDepartureTime(request.getDepartureTime());
        }
        if (request.getArrivalTime() != null) {
            entity.setArrivalTime(request.getArrivalTime());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getOperatingDays() != null) {
            entity.setOperatingDays(request.getOperatingDays());
        }
        if (request.getIsActive() != null) {
            entity.setActive(request.getIsActive());
        }
    }
}
