package com.easy.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.location.domain.TravelPlan;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
}
