package com.micro.airline_core_service.controller;

import com.micro.airline_core_service.service.AircraftService;
import com.micro.payload.request.AircraftRequest;
import com.micro.payload.response.AircraftResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId) {
        AircraftResponse createdAircraft = aircraftService.createAircraft(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) {
        AircraftResponse response = aircraftService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/owner")
    public ResponseEntity<List<AircraftResponse>> listAllAircraftByOwner(@RequestHeader("X-User-Id") Long ownerId) {
        List<AircraftResponse> response = aircraftService.listAllAircraftByOwner(ownerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long ownerId) {
        AircraftResponse updatedAircraft = aircraftService.updateAircraft(id, request, ownerId);
        return ResponseEntity.ok(updatedAircraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId) {
        aircraftService.deleteAircraft(id, ownerId);
        return ResponseEntity.ok("aircraft deleted");
    }
}
