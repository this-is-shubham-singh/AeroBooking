package com.micro.repository;

import com.micro.model.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FareRepository extends JpaRepository<Fare, Long> {

    boolean existsByFlightIdAndCabinClassIdAndName(Long flightId, Long cabinClassId, String name);

    boolean existsByFlightIdAndCabinClassIdAndNameAndIdNot(Long flightId, Long cabinClassId, String name, Long id);

    List<Fare> findByFlightIdAndCabinClassId(Long flightId, Long cabinClassId);

    List<Fare> findByFlightIdInAndCabinClassId(List<Long> flightIds, Long cabinClassId);
}
