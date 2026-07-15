package com.micro.airline_core_service.controller;

import com.micro.airline_core_service.service.AirlineService;
import com.micro.enums.AirlineStatus;
import com.micro.payload.request.AirlineRequest;
import com.micro.payload.response.AirlineDropdownItem;
import com.micro.payload.response.AirlineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long ownerId) {
        AirlineResponse createdAirline = airlineService.createAirline(request, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAirline);
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long ownerId) {
        AirlineResponse updatedAirline = airlineService.updateAirline(request, ownerId);
        return ResponseEntity.ok(updatedAirline);
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(@RequestHeader("X-User-Id") Long ownerId) {
        AirlineResponse airlineResponse = airlineService.getAirlineByOwner(ownerId);
        return ResponseEntity.ok(airlineResponse);
    }

    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlineDropdown() {
        List<AirlineDropdownItem> dropdown = airlineService.getAirlineDropdown();
        return ResponseEntity.ok(dropdown);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(@PathVariable Long id) {
        AirlineResponse airlineResponse = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airlineResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId) {
        airlineService.deleteAirline(id, ownerId);
        return ResponseEntity.ok("airline deleted");
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) {
        AirlineResponse airlineResponse = airlineService.changeStatusByAdmin(id, AirlineStatus.ACTIVE);
        return ResponseEntity.ok(airlineResponse);
    }

    @PutMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) {
        AirlineResponse airlineResponse = airlineService.changeStatusByAdmin(id, AirlineStatus.INACTIVE);
        return ResponseEntity.ok(airlineResponse);
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) {
        AirlineResponse airlineResponse = airlineService.changeStatusByAdmin(id, AirlineStatus.BANNED);
        return ResponseEntity.ok(airlineResponse);
    }

    @GetMapping
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AirlineResponse> airlines = airlineService.getAllAirlines(pageable);
        return ResponseEntity.ok(airlines);
    }
}
