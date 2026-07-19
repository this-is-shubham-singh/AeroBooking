package com.micro.flight_ops_service.Controller;

import com.micro.payload.request.FlightScheduleRequest;
import com.micro.payload.response.FlightScheduleResponse;
import com.micro.flight_ops_service.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/flight-schedules")
@RequiredArgsConstructor
public class FlightScheduleController {

    private final FlightScheduleService flightScheduleService;

    @PostMapping
    public ResponseEntity<FlightScheduleResponse> createFlightSchedule(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @Valid @RequestBody FlightScheduleRequest request) {
        return ResponseEntity.ok(flightScheduleService.createFlightSchedule(airlineId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> getFlightScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleById(id));
    }

    @GetMapping
    public ResponseEntity<List<FlightScheduleResponse>> getFlightScheduleByAirline(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(flightScheduleService.getFlightScheduleByAirline(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleResponse> updateFlightSchedule(
            @PathVariable Long id,
            @Valid @RequestBody FlightScheduleRequest request) {
        return ResponseEntity.ok(flightScheduleService.updateFlightSchedule(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlightSchedule(@PathVariable Long id) {
        flightScheduleService.deleteFlightSchedule(id);
        return ResponseEntity.ok("flight schedule deleted");
    }
}
