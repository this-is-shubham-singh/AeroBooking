package com.micro.location_service.service.impl;

import com.micro.location_service.model.City;
import com.micro.location_service.repository.CityRepository;
import com.micro.location_service.service.CityService;
import com.micro.payload.request.CityRequest;
import com.micro.payload.response.CityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public CityResponse createCity(CityRequest cityRequest) {
        if (cityRepository.existsByCityCode(cityRequest.getCityCode())) {
            throw new RuntimeException("City with code " + cityRequest.getCityCode() + " already exists");
        }
        City city = City.builder()
                .name(cityRequest.getName())
                .cityCode(cityRequest.getCityCode())
                .countryCode(cityRequest.getCountryCode())
                .countryName(cityRequest.getCountryName())
                .regionCode(cityRequest.getRegionCode())
                .timezoneId(cityRequest.getTimezoneOffset())
                .build();
        City savedCity = cityRepository.save(city);
        return mapToResponse(savedCity);
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        return mapToResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        
        city.setName(cityRequest.getName());
        city.setCityCode(cityRequest.getCityCode());
        city.setCountryCode(cityRequest.getCountryCode());
        city.setCountryName(cityRequest.getCountryName());
        city.setRegionCode(cityRequest.getRegionCode());
        city.setTimezoneId(cityRequest.getTimezoneOffset());

        City updatedCity = cityRepository.save(city);
        return mapToResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id: " + id));
        cityRepository.delete(city);
    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        
        Page<City> cityPage = cityRepository.findAll(pageable);

        Page<CityResponse> responsePage = cityPage.map(city -> this.mapToResponse(city));
        return responsePage;
    }


    // review this 
    // need to be review properly before allowing this
    @Override
    public Page<CityResponse> searchCities(String query, Pageable pageable) {
        return cityRepository.searchCities(query, pageable).map(this::mapToResponse);
    }

    @Override
    public Page<CityResponse> getCityByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCode(countryCode, pageable).map(this::mapToResponse);
    }

    @Override
    public boolean cityExist(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

    @Override
    public boolean validateCityCode(String code) {
        if (code == null) {
            return false;
        }
        return !code.trim().isEmpty() && code.length() <= 10;
    }

    private CityResponse mapToResponse(City city) {
        return CityResponse.builder()
                .name(city.getName())
                .cityCode(city.getCityCode())
                .countryCode(city.getCountryCode())
                .countryName(city.getCountryName())
                .build();
    }
}
