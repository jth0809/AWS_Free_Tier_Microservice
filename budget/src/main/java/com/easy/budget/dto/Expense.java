package com.easy.budget.dto;

public record Expense(
    String usage,
    Long amount,
    String time,
    String currency,
    String date
) {}
