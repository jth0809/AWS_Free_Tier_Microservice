package com.easy.location.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Travel {
    @Id
    @Column(name = "travel_id")
    private String id;

    private String memberId;

    private String title;

    private String location;

    private String startDate;

    private String endDate;

    private Long image;

    @OneToMany(mappedBy = "travel")
    private List<TravelPlan> travelPlans = new ArrayList<>();

    private String status;
}
