package com.micro.flight_ops_service.Controller;

import com.micro.payload.request.FlightInstanceRequest;
import com.micro.payload.response.FlightInstanceResponse;
import com.micro.flight_ops_service.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight-instances")
@RequiredArgsConstructor
public class FlightInstanceController {

    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<FlightInstanceResponse> createFlightInstance(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody FlightInstanceRequest request) {
        return ResponseEntity.ok(flightInstanceService.createFlightInstance(userId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> getFlightInstanceById(@PathVariable Long id) {
        return ResponseEntity.ok(flightInstanceService.getFlightInstanceById(id));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<Page<FlightInstanceResponse>> getByAirlineId(
            @PathVariable Long airlineId,
            @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) Long onDate,
            Pageable pageable) {
        return ResponseEntity.ok(flightInstanceService.getByAirlineId(
                airlineId, departureAirportId, arrivalAirportId, flightId, onDate, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightInstanceResponse> updateFlightInstance(
            @PathVariable Long id,
            @Valid @RequestBody FlightInstanceRequest request) {
        return ResponseEntity.ok(flightInstanceService.updateFlightInstance(id, request));
    }

    @DeleteMapping("/airline/{airlineId}/{id}")
    public ResponseEntity<String> deleteFlightInstance(
            @PathVariable Long airlineId,
            @PathVariable Long id) {
        flightInstanceService.deleteFlightInstance(airlineId, id);
        return ResponseEntity.ok("flight instance deleted");
    }
}
