package com.easy.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.budget.domain.BudgetManagement;
import java.util.List;

public interface BudgetRepository extends JpaRepository<BudgetManagement, Long> {
    List<BudgetManagement> findByMemberId(String memberId);
    void deleteByIdAndMemberId(Long id, String memberId);
}
