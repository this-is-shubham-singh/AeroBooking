package com.micro.service.Impl;

import com.micro.mapper.FareRulesMapper;
import com.micro.model.Fare;
import com.micro.model.FareRules;
import com.micro.payload.request.FareRulesRequest;
import com.micro.payload.response.FareRulesResponse;
import com.micro.repository.FareRepository;
import com.micro.repository.FareRulesRepository;
import com.micro.service.FareRulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FareRulesServiceImpl implements FareRulesService {

    private final FareRulesRepository fareRulesRepository;
    private final FareRepository fareRepository;

    @Override
    @Transactional
    public FareRulesResponse createFareRules(FareRulesRequest request) {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new RuntimeException("Fare not found with ID: " + request.getFareId()));

        FareRules fareRules = FareRulesMapper.toEntity(request, fare);
        FareRules savedRules = fareRulesRepository.save(fareRules);

        // Update the owning side of OneToOne relationship
        fare.setFareRules(savedRules);
        fareRepository.save(fare);

        return FareRulesMapper.toResponse(savedRules);
    }

    @Override
    public FareRulesResponse getFareRulesById(Long id) {
        FareRules fareRules = fareRulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FareRules not found with ID: " + id));
        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public FareRulesResponse getFareRulesByFareId(Long fareId) {
        FareRules fareRules = fareRulesRepository.findByFareId(fareId)
                .orElseThrow(() -> new RuntimeException("FareRules not found for fare ID: " + fareId));
        return FareRulesMapper.toResponse(fareRules);
    }

    @Override
    public List<FareRulesResponse> getFareRulesByAirlineId(Long airlineId) {
        List<FareRules> rulesList = fareRulesRepository.findByAirlineId(airlineId);
        return rulesList.stream()
                .map(FareRulesMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FareRulesResponse updateFareRules(Long id, FareRulesRequest request) {
        FareRules fareRules = fareRulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FareRules not found with ID: " + id));

        // Validate that the request is not trying to change the associated Fare ID
        if (fareRules.getFare() != null && !fareRules.getFare().getId().equals(request.getFareId())) {
            throw new RuntimeException("Cannot change the associated Fare ID of an existing rule.");
        }

        FareRulesMapper.updateEntity(request, fareRules);
        FareRules updatedRules = fareRulesRepository.save(fareRules);

        return FareRulesMapper.toResponse(updatedRules);
    }

    @Override
    @Transactional
    public void deleteFareRules(Long id) {
        FareRules fareRules = fareRulesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FareRules not found with ID: " + id));

        // Break the foreign key relationship link on the Fare table first to prevent SQL errors
        Fare fare = fareRules.getFare();
        if (fare != null) {
            fare.setFareRules(null);
            fareRepository.save(fare);
        }

        fareRulesRepository.delete(fareRules);
    }
}
