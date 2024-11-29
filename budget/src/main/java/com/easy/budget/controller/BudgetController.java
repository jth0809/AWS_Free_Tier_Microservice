package com.easy.budget.controller;

import org.springframework.web.bind.annotation.RestController;

import com.easy.budget.domain.Budget;
import com.easy.budget.dto.BudgetList;
import com.easy.budget.dto.Expense;
import com.easy.budget.service.BudgetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class BudgetController {
    BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @GetMapping("/budget")
    public Mono<BudgetList> getMyBudgets() {
        return budgetService.getMyBudgets().flatMap(expenses -> {
            return Mono.just(new BudgetList("name",expenses));
        });
    }

    @PostMapping("/budget")
    public Mono<String> addBudget(@RequestBody Expense budget) {
        System.out.println(budget);
        return budgetService.addBudget(budget).then(Mono.just("Budget added successfully"));
    }

    @PutMapping("/budget")
    public Mono<String> updateBudget(@RequestBody List<Expense> budget) {
    return budgetService.deleteBudget()
        .thenMany(Flux.fromIterable(budget)
            .flatMap(budgetService::addBudget))
        .then(Mono.just("Budget updated successfully"));
}

    @DeleteMapping("/budget")
    public Mono<String> deleteBudget(@RequestBody List<Expense> budget) {
        budgetService.deleteBudget();
        return Mono.just("Budget deleted successfully");
    }
    /* 
    @GetMapping("/budget/test")
    public String budgettest() {
        Budget budget = new Budget("Test", "Test", List.of(1.0, 2.0, 3.0));
        Expense budgetdto = new Expense(1L, "Test", List.of(budget));
        budgetService.addBudget(budgetdto);
        return "Budget added successfully";
    }

    @GetMapping("/budget/test2")
    public String bt2() {
        Budget budget = new Budget("Test", "Test", List.of(1.0, 2.0, 3.0));
        Expense budgetdto = new Expense(1L, "Test", List.of(budget));
        budgetService.updateBudget(budgetdto);
        return "Budget update successfully";
    }

    @GetMapping("/budget/test3")
    public String bt3() {
        budgetService.deleteBudget();
        return "Budget delete successfully";
    }
    */
    
}
