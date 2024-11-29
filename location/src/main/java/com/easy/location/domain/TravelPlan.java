package com.easy.location.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TravelPlan {
    @Id
    @Column(name = "travel_plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String memberId;

    private String title;

    private String description;

    private String startDate;

    private String endDate;

    private String image;

    @ManyToOne
    @JoinColumn(name = "travel_location_id")
    private TravelLocation travelLocation;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;
}
