package com.transaction.orchestration.dto;

public record Plandto(
    Long id,
    
    String title,

    String description,

    String startDate,

    String endDate,

    String image,

    TravelLocation travelPlanLocation
) {}
