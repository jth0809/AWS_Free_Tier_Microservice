package com.easy.edge.dto;

import java.util.List;

public record Budget (
    String title,
    String description,
    List<Double> amount
){}

