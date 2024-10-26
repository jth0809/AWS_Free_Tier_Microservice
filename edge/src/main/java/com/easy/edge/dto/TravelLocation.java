package com.easy.edge.dto;

public record TravelLocation (
     Long id,
    
     String travelLocationName,

     String travelLocationDescription,
    
     Location travelLocation,

     String travelLocationImage,

     Integer travelLocationRating,

     String travelLocationReview
){}
