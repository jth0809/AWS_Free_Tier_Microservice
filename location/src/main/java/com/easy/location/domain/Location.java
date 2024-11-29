package com.easy.location.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Getter
@Setter
public class Location {
    
    private Double latitude;
    
    private Double longitude;
}
