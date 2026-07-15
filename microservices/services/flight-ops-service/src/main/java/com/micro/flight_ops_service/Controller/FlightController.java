package com.micro.flight_ops_service.Controller;

import com.micro.enums.FlightStatus;
import com.micro.payload.request.FlightRequest;
import com.micro.payload.response.FlightResponse;
import com.micro.flight_ops_service.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/airline/{airlineId}")
    public ResponseEntity<FlightResponse> createFlight(
            @PathVariable Long airlineId,
            @Valid @RequestBody FlightRequest flightRequest) {
        return ResponseEntity.ok(flightService.createFlight(airlineId, flightRequest));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<Page<FlightResponse>> getFlightsByAirline(
            @PathVariable Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable) {
        return ResponseEntity
                .ok(flightService.getFlightsByAirline(airlineId, departureAirportId, arrivalAirportId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(
            @PathVariable Long id,
            @Valid @RequestBody FlightRequest flightRequest) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightRequest));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam FlightStatus status) {
        return ResponseEntity.ok(flightService.changeStatus(id, status));
    }

    @DeleteMapping("/airline/{airlineId}/{id}")
    public ResponseEntity<String> deleteFlight(
            @PathVariable Long airlineId,
            @PathVariable Long id) {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.ok("flight deleted");
    }
}
