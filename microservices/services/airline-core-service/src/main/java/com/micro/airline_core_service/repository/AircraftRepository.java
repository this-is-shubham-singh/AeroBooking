package com.micro.airline_core_service.repository;

import com.micro.airline_core_service.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    boolean existsByCode(String code);

    List<Aircraft> findByAirlineId(Long airlineId);

    Optional<Aircraft> findByCode(String code);
}
