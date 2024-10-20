package com.easy.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.location.domain.TravelLocation;
import java.util.Optional;


public interface TravelLocationRepository extends JpaRepository<TravelLocation, Long> {
    Optional<TravelLocation> findByTravelLocationName(String travelLocationName);
}
