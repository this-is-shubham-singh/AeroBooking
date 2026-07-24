package com.micro.service.Impl;

import com.micro.mapper.BaggagePolicyMapper;
import com.micro.model.BaggagePolicy;
import com.micro.model.Fare;
import com.micro.payload.request.BaggagePolicyRequest;
import com.micro.payload.response.BaggagePolicyResponse;
import com.micro.repository.BaggagePolicyRepository;
import com.micro.repository.FareRepository;
import com.micro.service.BaggagePolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BaggagePolicyServiceImpl implements BaggagePolicyService {

    private final BaggagePolicyRepository baggagePolicyRepository;
    private final FareRepository fareRepository;

    @Override
    @Transactional
    public BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request) {
        Fare fare = fareRepository.findById(request.getFareId())
                .orElseThrow(() -> new RuntimeException("Fare not found with ID: " + request.getFareId()));

        if (fare.getBaggagePolicy() != null) {
            throw new RuntimeException("Baggage policy already exists for this fare ID: " + request.getFareId());
        }

        BaggagePolicy baggagePolicy = BaggagePolicyMapper.toEntity(request, fare);
        BaggagePolicy savedPolicy = baggagePolicyRepository.save(baggagePolicy);

        // Update the owning side of the OneToOne relationship in the Fare table
        fare.setBaggagePolicy(savedPolicy);
        fareRepository.save(fare);

        return BaggagePolicyMapper.toResponse(savedPolicy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyById(Long id) {
        BaggagePolicy policy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaggagePolicy not found with ID: " + id));
        return BaggagePolicyMapper.toResponse(policy);
    }

    @Override
    public BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId) {
        BaggagePolicy policy = baggagePolicyRepository.findByFareId(fareId)
                .orElseThrow(() -> new RuntimeException("BaggagePolicy not found for fare ID: " + fareId));
        return BaggagePolicyMapper.toResponse(policy);
    }

    @Override
    public List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId) {
        List<BaggagePolicy> policies = baggagePolicyRepository.findByAirlineId(airlineId);
        return policies.stream()
                .map(BaggagePolicyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaggagePolicy not found with ID: " + id));

        // Validate that the request is not trying to change the associated Fare ID
        if (baggagePolicy.getFare() != null && !baggagePolicy.getFare().getId().equals(request.getFareId())) {
            throw new RuntimeException("Cannot change the associated Fare ID of an existing baggage policy.");
        }

        BaggagePolicyMapper.updateEntity(request, baggagePolicy);
        BaggagePolicy updatedPolicy = baggagePolicyRepository.save(baggagePolicy);

        return BaggagePolicyMapper.toResponse(updatedPolicy);
    }

    @Override
    @Transactional
    public void deleteBaggagePolicy(Long id) {
        BaggagePolicy baggagePolicy = baggagePolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BaggagePolicy not found with ID: " + id));

        // Break the foreign key relationship link on the Fare table first to prevent SQL errors
        Fare fare = baggagePolicy.getFare();
        if (fare != null) {
            fare.setBaggagePolicy(null);
            fareRepository.save(fare);
        }

        baggagePolicyRepository.delete(baggagePolicy);
    }
}
