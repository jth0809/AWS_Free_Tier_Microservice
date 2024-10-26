package com.easy.edge.dto;

public record Plandto(
    Long id,
    
    String title,

    String description,

    String startDate,

    String endDate,

    String image,

    TravelLocation travelPlanLocation
) {}
