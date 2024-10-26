package com.transaction.orchestration.dto;

import java.util.List;

public record Budget (
    String title,
    String description,
    List<Double> amount
){}

