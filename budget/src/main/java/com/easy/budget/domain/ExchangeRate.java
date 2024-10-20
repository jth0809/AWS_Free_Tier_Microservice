package com.easy.budget.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ExchangeRate {
    @Id
    private String currencyCode;

    private Double rate;
}
