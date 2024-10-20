package com.easy.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.easy.budget.dto.BudgetDto;
import com.easy.budget.service.BudgetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class BudgetController {
    BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @GetMapping("/budget")
    public List<BudgetDto> getMyBudgets() {
        return budgetService.getMyBudgets();
    }

    @PostMapping("/budget")
    public void addBudget(@RequestBody BudgetDto budget) {
        budgetService.addBudget(budget);
    }

    @PutMapping("/budget")
    public void updateBudget(@RequestBody BudgetDto budget) {
        budgetService.updateBudget(budget);
    }

    @DeleteMapping("/budget")
    public void deleteBudget(@RequestBody BudgetDto budget) {
        budgetService.deleteBudget(budget);
    }
}
