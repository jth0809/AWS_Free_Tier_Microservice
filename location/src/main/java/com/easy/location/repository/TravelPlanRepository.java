package com.easy.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.location.domain.TravelPlan;

import java.util.List;
import java.util.Optional;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
    List<TravelPlan> findAllBymemberId(String memberId);
    Optional<TravelPlan> findByUuidAndMemberId(String id, String memberId);
    Optional<TravelPlan> findByUuid(String uuid);
    void deleteByUuid(String uuid);
}
