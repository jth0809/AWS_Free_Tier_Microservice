package com.easy.location.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.location.domain.Travel;

public interface TravelRepository extends JpaRepository<Travel, String> {
    List<Travel> findAllBymemberId(String memberId);
}
