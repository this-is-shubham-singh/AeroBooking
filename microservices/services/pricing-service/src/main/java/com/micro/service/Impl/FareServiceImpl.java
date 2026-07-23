package com.micro.service.Impl;

import com.micro.mapper.FareMapper;
import com.micro.model.Fare;
import com.micro.payload.request.FareRequest;
import com.micro.payload.response.FareResponse;
import com.micro.repository.FareRepository;
import com.micro.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    @Override
    public FareResponse createFare(FareRequest request) {
        if (fareRepository.existsByFlightIdAndCabinClassIdAndName(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName())) {
            throw new RuntimeException("Fare with name '" + request.getName() +
                    "' already exists for this flight and cabin class.");
        }

        Fare fare = FareMapper.toEntity(request);
        Fare savedFare = fareRepository.save(fare);
        return FareMapper.toResponse(savedFare);
    }

    @Override
    public FareResponse getFareById(Long id) {
        Fare fare = fareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fare not found with ID: " + id));
        return FareMapper.toResponse(fare);
    }

    @Override
    public List<FareResponse> getFaresByFlightIdAndCabinClassId(Long flightId, Long cabinClassId) {
        List<Fare> fares = fareRepository.findByFlightIdAndCabinClassId(flightId, cabinClassId);
        return fares.stream()
                .map(FareMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FareResponse updateFare(Long id, FareRequest request) {
        Fare fare = fareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fare not found with ID: " + id));

        if (fareRepository.existsByFlightIdAndCabinClassIdAndNameAndIdNot(
                request.getFlightId(),
                request.getCabinClassId(),
                request.getName(),
                fare.getId())) {
            throw new RuntimeException("Another fare with name '" + request.getName() +
                    "' already exists for this flight and cabin class.");
        }

        FareMapper.updateEntity(request, fare);
        Fare updatedFare = fareRepository.save(fare);
        return FareMapper.toResponse(updatedFare);
    }

    @Override
    public void deleteFare(Long id) {
        Fare fare = fareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fare not found with ID: " + id));
        fareRepository.delete(fare);
    }

    @Override
    public List<Fare> getFares() {
        return fareRepository.findAll();
    }

    @Override
    public Map<Long, FareResponse> getLowestFarePerFlight(List<Long> flightIds, Long cabinClassId) {
        if (flightIds == null || flightIds.isEmpty() || cabinClassId == null) {
            return Map.of();
        }

        List<Fare> fares = fareRepository.findByFlightIdInAndCabinClassId(flightIds, cabinClassId);
        Map<Long, Fare> lowestFaresMap = new HashMap<>();

        for (Fare fare : fares) {
            Long flightId = fare.getFlightId();

            if (!lowestFaresMap.containsKey(flightId)) {
                lowestFaresMap.put(flightId, fare);
            } else {
                Fare existingFare = lowestFaresMap.get(flightId);
                if (fare.getCurrentPrice() < existingFare.getCurrentPrice()) {
                    lowestFaresMap.put(flightId, fare);
                }
            }
        }

        Map<Long, FareResponse> responseMap = new HashMap<>();
        for (Map.Entry<Long, Fare> entry : lowestFaresMap.entrySet()) {
            responseMap.put(entry.getKey(), FareMapper.toResponse(entry.getValue()));
        }

        return responseMap;
    }

    @Override
    public Map<Long, FareResponse> getFaresByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Map.of();
        }

        List<Fare> fares = fareRepository.findAllById(ids);
        Map<Long, FareResponse> responseMap = new HashMap<>();

        for (Fare fare : fares) {
            responseMap.put(fare.getId(), FareMapper.toResponse(fare));
        }

        return responseMap;
    }
}
