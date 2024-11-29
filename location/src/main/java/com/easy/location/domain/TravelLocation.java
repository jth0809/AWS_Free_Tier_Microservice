package com.easy.location.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
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

    private Long travelLocationRating;

    private String travelLocationReview;

    public TravelLocation(String travelLocationName, String travelLocationDescription, Location travelLocation, String travelLocationImage, Long travelLocationRating, String travelLocationReview) {
        this.travelLocationName = travelLocationName;
        this.travelLocationDescription = travelLocationDescription;
        this.travelLocation = travelLocation;
        this.travelLocationImage = travelLocationImage;
        this.travelLocationRating = travelLocationRating;
        this.travelLocationReview = travelLocationReview;
    }
}
