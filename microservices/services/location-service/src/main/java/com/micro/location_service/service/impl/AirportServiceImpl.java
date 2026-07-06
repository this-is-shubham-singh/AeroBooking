package com.micro.location_service.service.impl;

import com.micro.location_service.model.Airport;
import com.micro.location_service.model.City;
import com.micro.location_service.repository.AirportRepository;
import com.micro.location_service.repository.CityRepository;
import com.micro.location_service.service.AirportService;
import com.micro.payload.request.AirportRequest;
import com.micro.payload.response.AirportResponse;
import com.micro.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) {
        if (airportRepository.existsByIataCode(airportRequest.getIataCode())) {
            throw new RuntimeException("Airport with IATA code " + airportRequest.getIataCode() + " already exists");
        }

        City city = cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + airportRequest.getCityId()));

        Airport airport = Airport.builder()
                .iataCode(airportRequest.getIataCode())
                .name(airportRequest.getName())
                .address(airportRequest.getAddress())
                .geoCode(airportRequest.getGetcode())
                .timezone(airportRequest.getTimezone() != null ? airportRequest.getTimezone().getId() : null)
                .city(city)
                .build();

        Airport savedAirport = airportRepository.save(airport);
        return mapToResponse(savedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
        return mapToResponse(airport);
    }

    @Override
    public Page<AirportResponse> getAllAirports(Pageable pageable) {
        return airportRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));

        City city = cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found with id: " + airportRequest.getCityId()));

        airport.setIataCode(airportRequest.getIataCode());
        airport.setName(airportRequest.getName());
        airport.setAddress(airportRequest.getAddress());
        airport.setGeoCode(airportRequest.getGetcode());
        airport.setTimezone(airportRequest.getTimezone() != null ? airportRequest.getTimezone().getId() : null);
        airport.setCity(city);

        Airport updatedAirport = airportRepository.save(airport);
        return mapToResponse(updatedAirport);
    }

    @Override
    public void deleteAirport(Long id) {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found with id: " + id));
        airportRepository.delete(airport);
    }

    @Override
    public List<AirportResponse> getAirportsByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AirportResponse mapToResponse(Airport airport) {
        CityResponse cityResponse = null;
        if (airport.getCity() != null) {
            cityResponse = CityResponse.builder()
                    .name(airport.getCity().getName())
                    .cityCode(airport.getCity().getCityCode())
                    .countryCode(airport.getCity().getCountryCode())
                    .countryName(airport.getCity().getCountryName())
                    .build();
        }

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getName() + " (" + airport.getIataCode() + ")")
                .timezone(airport.getTimezone())
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .city(cityResponse)
                .build();
    }
}
