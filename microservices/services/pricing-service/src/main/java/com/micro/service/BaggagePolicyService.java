package com.micro.service;

import com.micro.payload.request.BaggagePolicyRequest;
import com.micro.payload.response.BaggagePolicyResponse;
import java.util.List;

public interface BaggagePolicyService {

    BaggagePolicyResponse createBaggagePolicy(BaggagePolicyRequest request);

    BaggagePolicyResponse getBaggagePolicyById(Long id);

    BaggagePolicyResponse getBaggagePolicyByFareId(Long fareId);

    List<BaggagePolicyResponse> getBaggagePoliciesByAirlineId(Long airlineId);

    BaggagePolicyResponse updateBaggagePolicy(Long id, BaggagePolicyRequest request);

    void deleteBaggagePolicy(Long id);
}
