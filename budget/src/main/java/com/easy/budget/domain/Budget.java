package com.easy.budget.domain;

import jakarta.persistence.Embeddable;
import java.util.List;

@Embeddable
public class Budget {
    private String title;
    private String description;
    private List<Double> amount;
}
