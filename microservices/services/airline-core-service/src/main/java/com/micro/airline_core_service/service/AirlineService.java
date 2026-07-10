package com.micro.airline_core_service.service;

import com.micro.enums.AirlineStatus;
import com.micro.payload.request.AirlineRequest;
import com.micro.payload.response.AirlineDropdownItem;
import com.micro.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request, Long ownerId);

    AirlineResponse getAirlineByOwner(Long ownerId);

    AirlineResponse getAirlineById(Long id);

    Page<AirlineResponse> getAllAirlines(Pageable pageable);

    AirlineResponse updateAirline(AirlineRequest request, Long ownerId);

    void deleteAirline(Long id, Long ownerId);

    AirlineResponse changeStatusByAdmin(Long airlineId, AirlineStatus status);

    List<AirlineDropdownItem> getAirlineDropdown();
}
