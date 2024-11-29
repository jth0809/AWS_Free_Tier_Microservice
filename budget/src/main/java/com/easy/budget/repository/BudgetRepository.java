package com.easy.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easy.budget.domain.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findAllByMemberId(String memberId);
    
    Optional<Budget> findByIdAndMemberId(Long id, String memberId);

    void deleteByMemberId(String memberId);

    Optional<Budget> findByMemberId(String memberId);
}
