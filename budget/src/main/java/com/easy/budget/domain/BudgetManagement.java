package com.easy.budget.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BudgetManagement {

    @Id
    private Long id;

    private Long memberId;

    @OneToMany(mappedBy = "bugetManagement")
    private List<ExchangeRate> exchangeRates = new ArrayList<>();

}
