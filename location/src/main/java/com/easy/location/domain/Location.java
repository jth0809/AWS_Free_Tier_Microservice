package com.easy.location.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Location {
    
    private Double latitude;
    
    private Double longitude;
}
