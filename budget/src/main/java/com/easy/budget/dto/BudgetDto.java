package com.easy.budget.dto;

import java.util.List;

import com.easy.budget.domain.Budget;

public record BudgetDto(
    Long id,
    String memberId,
    List<Budget> budget
) {}
