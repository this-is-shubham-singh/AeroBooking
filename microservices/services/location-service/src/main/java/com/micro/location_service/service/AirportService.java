package com.micro.location_service.service;

import com.micro.payload.request.AirportRequest;
import com.micro.payload.response.AirportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest);

    AirportResponse getAirportById(Long id);

    Page<AirportResponse> getAllAirports(Pageable pageable);

    AirportResponse updateAirport(Long id, AirportRequest airportRequest);

    void deleteAirport(Long id);

    List<AirportResponse> getAirportsByCityId(Long cityId);
}