package com.micro.airline_core_service.service.Impl;

import com.micro.enums.AirlineStatus;
import com.micro.payload.request.AirlineRequest;
import com.micro.payload.response.AirlineDropdownItem;
import com.micro.payload.response.AirlineResponse;
import com.micro.airline_core_service.model.Airline;
import com.micro.airline_core_service.repository.AirlineRepository;
import com.micro.airline_core_service.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        if (airlineRepository.existsByOwnerId(ownerId)) {
            throw new RuntimeException("Owner already owns an airline!");
        }
        if (airlineRepository.existsByIataCode(request.getIataCode())) {
            throw new RuntimeException("Airline with IATA code already exists!");
        }
        if (airlineRepository.existsByIcaoCode(request.getIcaoCode())) {
            throw new RuntimeException("Airline with ICAO code already exists!");
        }

        Airline airline = Airline.builder()
                .iataCode(request.getIataCode())
                .icaoCode(request.getIcaoCode())
                .name(request.getName())
                .alias(request.getAlias())
                .logoUrl(request.getLogoUrl())
                .website(request.getWebsite())
                .status(request.getStatus() != null ? request.getStatus() : AirlineStatus.ACTIVE)
                .alliance(request.getAlliance())
                .headquartersCityId(request.getHeadquartersCityId())
                .support(request.getSupport())
                .ownerId(ownerId)
                .build();

        Airline savedAirline = airlineRepository.save(airline);
        return convertToResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));
        return convertToResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + id));
        return convertToResponse(airline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {
        return airlineRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));

        airline.setName(request.getName());
        airline.setAlias(request.getAlias());
        airline.setLogoUrl(request.getLogoUrl());
        airline.setWebsite(request.getWebsite());
        if (request.getStatus() != null) {
            airline.setStatus(request.getStatus());
        }
        airline.setAlliance(request.getAlliance());
        airline.setHeadquartersCityId(request.getHeadquartersCityId());
        airline.setSupport(request.getSupport());
        airline.setUpdatedById(ownerId);

        Airline updatedAirline = airlineRepository.save(airline);
        return convertToResponse(updatedAirline);
    }

    @Override
    public void deleteAirline(Long id, Long ownerId) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + id));

        if (!airline.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized: You do not own this airline!");
        }

        airlineRepository.delete(airline);
    }

    @Override
    public AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status) {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new RuntimeException("Airline not found with ID: " + airlineId));

        airline.setStatus(status);
        Airline updatedAirline = airlineRepository.save(airline);
        return convertToResponse(updatedAirline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdown() {
        return airlineRepository.findAll()
                .stream()
                .map(this::convertToDropdownItem)
                .collect(Collectors.toList());
    }

    private AirlineResponse convertToResponse(Airline airline) {
        return AirlineResponse.builder()
                .id(airline.getId())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .name(airline.getName())
                .alias(airline.getAlias())
                .logoUrl(airline.getLogoUrl())
                .website(airline.getWebsite())
                .status(airline.getStatus())
                .alliance(airline.getAlliance())
                .headquartersCityId(airline.getHeadquartersCityId())
                .ownerId(airline.getOwnerId())
                .support(airline.getSupport())
                .updatedById(airline.getUpdatedById())
                .createdAt(airline.getCreatedAt())
                .updatedAt(airline.getUpdatedAt())
                .build();
    }

    private AirlineDropdownItem convertToDropdownItem(Airline airline) {
        return AirlineDropdownItem.builder()
                .id(airline.getId())
                .name(airline.getName())
                .iataCode(airline.getIataCode())
                .icaoCode(airline.getIcaoCode())
                .logoUrl(airline.getLogoUrl())
                .build();
    }
}
