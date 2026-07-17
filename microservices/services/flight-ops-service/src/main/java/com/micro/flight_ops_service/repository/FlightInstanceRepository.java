package com.micro.flight_ops_service.repository;

import com.micro.flight_ops_service.model.FlightInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {

    @Query("""
        select fi from FlightInstance fi
        where fi.airlineId = :airlineId
        and (:depId is null or fi.departureAirportId = :depId)
        and (:arrId is null or fi.arrivalAirportId = :arrId)
        and (:flightId is null or fi.flight.id = :flightId)
        and (:dayStart is null or fi.departureDateTime >= :dayStart)
        and (:dayEnd is null or fi.departureDateTime <= :dayEnd)
    """)
    Page<FlightInstance> findByFilters(
            @Param("airlineId") Long airlineId,
            @Param("depId") Long departureAirportId,
            @Param("arrId") Long arrivalAirportId,
            @Param("flightId") Long flightId,
            @Param("dayStart") LocalDateTime dayStart,
            @Param("dayEnd") LocalDateTime dayEnd,
            Pageable pageable
    );
}
