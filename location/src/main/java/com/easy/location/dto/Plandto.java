package com.easy.location.dto;

import com.easy.location.domain.TravelLocation;

public record Plandto(
    String id,

    String memberId,
    
    String title,

    String description, //memo

    String startDate,

    String endDate,

    String image, //location

    TravelLocation travelPlanLocation
) {}
