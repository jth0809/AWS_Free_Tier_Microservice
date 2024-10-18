package com.easy.location.domain;

import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "travelLocation")
    private List<TravelPlan> travelPlans = new ArrayList<>();
}
