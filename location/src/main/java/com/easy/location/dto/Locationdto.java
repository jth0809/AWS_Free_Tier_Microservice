package com.easy.location.dto;

import com.easy.location.domain.Location;

public record Locationdto(
    Long id,
    
    String travelLocationName,

    String travelLocationDescription,
    
    Location travelLocation,

    String travelLocationImage,

    Long travelLocationRating,

    String travelLocationReview
){}
