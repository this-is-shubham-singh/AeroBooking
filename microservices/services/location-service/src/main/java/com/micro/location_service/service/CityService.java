package com.micro.location_service.service;

import com.micro.payload.request.CityRequest;
import com.micro.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CityService {

    CityResponse createCity(CityRequest cityRequest);

    CityResponse getCityById(Long id);

    CityResponse updateCity(Long id, CityRequest cityRequest);

    void deleteCity(Long id);

    Page<CityResponse> getAllCities(Pageable pageable);

    Page<CityResponse> searchCities(String query, Pageable pageable);

    Page<CityResponse> getCityByCountryCode(String countryCode, Pageable pageable);

    boolean cityExist(String cityCode);

    boolean validateCityCode(String code);
}