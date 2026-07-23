package com.micro.controller;

import com.micro.payload.request.FareRulesRequest;
import com.micro.payload.response.FareRulesResponse;
import com.micro.service.FareRulesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/fare-rules")
@RequiredArgsConstructor
public class FareRulesController {

    private final FareRulesService fareRulesService;

    @PostMapping
    public ResponseEntity<FareRulesResponse> createFareRules(@Valid @RequestBody FareRulesRequest request) {
        return ResponseEntity.ok(fareRulesService.createFareRules(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FareRulesResponse> getFareRulesById(@PathVariable Long id) {
        return ResponseEntity.ok(fareRulesService.getFareRulesById(id));
    }

    @GetMapping("/fare/{fareId}")
    public ResponseEntity<FareRulesResponse> getFareRulesByFareId(@PathVariable Long fareId) {
        return ResponseEntity.ok(fareRulesService.getFareRulesByFareId(fareId));
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<List<FareRulesResponse>> getFareRulesByAirlineId(@PathVariable Long airlineId) {
        return ResponseEntity.ok(fareRulesService.getFareRulesByAirlineId(airlineId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FareRulesResponse> updateFareRules(
            @PathVariable Long id,
            @Valid @RequestBody FareRulesRequest request) {
        return ResponseEntity.ok(fareRulesService.updateFareRules(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFareRules(@PathVariable Long id) {
        fareRulesService.deleteFareRules(id);
        return ResponseEntity.ok("Fare rules deleted successfully");
    }
}
