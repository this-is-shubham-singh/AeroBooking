package com.micro.location_service.repository;

import com.micro.location_service.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByCityCode(String cityCode);

    Page<City> findByCountryCode(String countryCode, Pageable pageable);

    // review this properly 
    @Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(c.cityCode) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<City> searchCities(@Param("query") String query, Pageable pageable);
}
