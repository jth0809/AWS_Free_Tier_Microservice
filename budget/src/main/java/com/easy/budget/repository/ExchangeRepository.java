package com.easy.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easy.budget.domain.ExchangeRate;


public interface ExchangeRepository extends JpaRepository<ExchangeRate, String>{
}
