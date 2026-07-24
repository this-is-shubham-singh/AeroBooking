package com.micro.flight_ops_service.service;

import com.micro.payload.request.FlightScheduleRequest;
import com.micro.payload.response.FlightScheduleResponse;
import java.util.List;

public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule(Long airlineId, FlightScheduleRequest flightScheduleRequest);

    FlightScheduleResponse getFlightScheduleById(Long id);

    List<FlightScheduleResponse> getFlightScheduleByAirline(Long userId);

    FlightScheduleResponse updateFlightSchedule(Long id, FlightScheduleRequest flightScheduleRequest);

    void deleteFlightSchedule(Long id);
}
