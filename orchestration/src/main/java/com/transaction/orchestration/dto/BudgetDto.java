package com.transaction.orchestration.dto;

import java.util.List;

public record BudgetDto(
    Long id,
    String memberId,
    List<Budget> budget
) {}
