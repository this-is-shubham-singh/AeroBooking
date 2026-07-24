package com.micro.repository;

import com.micro.model.BaggagePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy, Long> {

    Optional<BaggagePolicy> findByFareId(Long fareId);

    List<BaggagePolicy> findByAirlineId(Long airlineId);
}
