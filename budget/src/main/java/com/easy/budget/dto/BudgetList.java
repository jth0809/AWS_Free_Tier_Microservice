package com.easy.budget.dto;

import java.util.List;

public record BudgetList (
    String key,
    List<Expense>value
){}
