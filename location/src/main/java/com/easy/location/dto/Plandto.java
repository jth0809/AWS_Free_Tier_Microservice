package com.easy.location.dto;

import com.easy.location.domain.TravelLocation;

public record Plandto(
    Long id,
    
    String title,

    String description,

    String startDate,

    String endDate,

    String image,

    TravelLocation travelPlanLocation
) {}
