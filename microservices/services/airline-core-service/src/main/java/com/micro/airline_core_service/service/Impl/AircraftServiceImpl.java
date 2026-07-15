package com.micro.airline_core_service.service.Impl;

import com.micro.enums.AircraftStatus;
import com.micro.payload.request.AircraftRequest;
import com.micro.payload.response.AircraftResponse;
import com.micro.airline_core_service.model.Aircraft;
import com.micro.airline_core_service.model.Airline;
import com.micro.airline_core_service.repository.AircraftRepository;
import com.micro.airline_core_service.repository.AirlineRepository;
import com.micro.airline_core_service.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));

        if (aircraftRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Aircraft with code already exists: " + request.getCode());
        }

        Aircraft aircraft = Aircraft.builder()
                .code(request.getCode())
                .model(request.getModel())
                .manufacturer(request.getManufacturer())
                .seatingCapacity(request.getSeatingCapacity())
                .economySeats(request.getEconomySeats() != null ? request.getEconomySeats() : 0)
                .premiumEconomySeats(request.getPremiumEconomySeats() != null ? request.getPremiumEconomySeats() : 0)
                .businessSeats(request.getBusinessSeats() != null ? request.getBusinessSeats() : 0)
                .firstClassSeats(request.getFirstClassSeats() != null ? request.getFirstClassSeats() : 0)
                .rangeKm(request.getRangeKm())
                .cruisingSpeedKmh(request.getCruisingSpeedKmh())
                .maxAltitudeFt(request.getMaxAltitudeFt())
                .yearOfManufacture(request.getYearOfManufacture())
                .registrationDate(request.getRegistrationDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .status(request.getStatus() != null ? request.getStatus() : AircraftStatus.ACTIVE)
                .isAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true)
                .airline(airline)
                .currentAirportId(request.getCurrentAirportId())
                .build();

        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return convertToResponse(savedAircraft);
    }

    @Override
    public AircraftResponse getById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with ID: " + id));
        return convertToResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> listAllAircraftByOwner(Long ownerId) {
        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));

        return aircraftRepository.findByAirlineId(airline.getId())
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AircraftResponse updateAircraft(Long aircraftId, AircraftRequest request, Long ownerId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with ID: " + aircraftId));

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));

        if (!aircraft.getAirline().getId().equals(airline.getId())) {
            throw new RuntimeException("Unauthorized: You do not own this aircraft!");
        }

        if (request.getCode() != null && !aircraft.getCode().equals(request.getCode()) 
                && aircraftRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Aircraft with code already exists: " + request.getCode());
        }

        // Safe null-checks for partial updates
        if (request.getCode() != null) aircraft.setCode(request.getCode());
        if (request.getModel() != null) aircraft.setModel(request.getModel());
        if (request.getManufacturer() != null) aircraft.setManufacturer(request.getManufacturer());
        if (request.getSeatingCapacity() != null) aircraft.setSeatingCapacity(request.getSeatingCapacity());
        if (request.getEconomySeats() != null) aircraft.setEconomySeats(request.getEconomySeats());
        if (request.getPremiumEconomySeats() != null) aircraft.setPremiumEconomySeats(request.getPremiumEconomySeats());
        if (request.getBusinessSeats() != null) aircraft.setBusinessSeats(request.getBusinessSeats());
        if (request.getFirstClassSeats() != null) aircraft.setFirstClassSeats(request.getFirstClassSeats());
        if (request.getRangeKm() != null) aircraft.setRangeKm(request.getRangeKm());
        if (request.getCruisingSpeedKmh() != null) aircraft.setCruisingSpeedKmh(request.getCruisingSpeedKmh());
        if (request.getMaxAltitudeFt() != null) aircraft.setMaxAltitudeFt(request.getMaxAltitudeFt());
        if (request.getYearOfManufacture() != null) aircraft.setYearOfManufacture(request.getYearOfManufacture());
        if (request.getRegistrationDate() != null) aircraft.setRegistrationDate(request.getRegistrationDate());
        if (request.getNextMaintenanceDate() != null) aircraft.setNextMaintenanceDate(request.getNextMaintenanceDate());
        if (request.getStatus() != null) aircraft.setStatus(request.getStatus());
        if (request.getIsAvailable() != null) aircraft.setIsAvailable(request.getIsAvailable());
        if (request.getCurrentAirportId() != null) aircraft.setCurrentAirportId(request.getCurrentAirportId());

        Aircraft updatedAircraft = aircraftRepository.save(aircraft);
        return convertToResponse(updatedAircraft);
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found with ID: " + id));

        Airline airline = airlineRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Airline not found for owner ID: " + ownerId));

        if (!aircraft.getAirline().getId().equals(airline.getId())) {
            throw new RuntimeException("Unauthorized: You do not own this aircraft!");
        }

        aircraftRepository.delete(aircraft);
    }

    private AircraftResponse convertToResponse(Aircraft aircraft) {
        return AircraftResponse.builder()
                .id(aircraft.getId())
                .code(aircraft.getCode())
                .model(aircraft.getModel())
                .manufacturer(aircraft.getManufacturer())
                .seatingCapacity(aircraft.getSeatingCapacity())
                .economySeats(aircraft.getEconomySeats())
                .premiumEconomySeats(aircraft.getPremiumEconomySeats())
                .businessSeats(aircraft.getBusinessSeats())
                .firstClassSeats(aircraft.getFirstClassSeats())
                .rangeKm(aircraft.getRangeKm())
                .cruisingSpeedKmh(aircraft.getCruisingSpeedKmh())
                .maxAltitudeFt(aircraft.getMaxAltitudeFt())
                .yearOfManufacture(aircraft.getYearOfManufacture())
                .registrationDate(aircraft.getRegistrationDate())
                .nextMaintenanceDate(aircraft.getNextMaintenanceDate())
                .status(aircraft.getStatus())
                .isAvailable(aircraft.getIsAvailable())
                .airlineId(aircraft.getAirline() != null ? aircraft.getAirline().getId() : null)
                .airlineName(aircraft.getAirline() != null ? aircraft.getAirline().getName() : null)
                .airlineIataCode(aircraft.getAirline() != null ? aircraft.getAirline().getIataCode() : null)
                .currentAirportId(aircraft.getCurrentAirportId())
                .totalSeats(aircraft.getTotalSeats())
                .requiresMaintenance(aircraft.requiresMaintenance())
                .isOperational(aircraft.isOperational())
                .createdAt(aircraft.getCreatedAt())
                .updatedAt(aircraft.getUpdatedAt())
                .build();
    }
}
