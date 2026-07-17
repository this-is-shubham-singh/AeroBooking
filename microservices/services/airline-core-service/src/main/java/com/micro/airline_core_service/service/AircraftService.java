package com.micro.airline_core_service.service;

import com.micro.payload.request.AircraftRequest;
import com.micro.payload.response.AircraftResponse;
import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest request, Long ownerId);

    AircraftResponse getById(Long id);

    List<AircraftResponse> listAllAircraftByOwner(Long ownerId);

    AircraftResponse updateAircraft(Long aircraftId, AircraftRequest request, Long ownerId);

    void deleteAircraft(Long id, Long ownerId);
}

