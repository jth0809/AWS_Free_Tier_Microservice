package com.easy.location.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class TravelLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_location_id")
    private Long id;
    
    private String travelLocationName;

    private String travelLocationDescription;
    
    @Embedded
    private Location travelLocation;

    private String travelLocationImage;

    private int travelLocationRating;

    private String travelLocationReview;
}
