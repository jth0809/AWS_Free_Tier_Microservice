package com.easy.budget.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.budget.repository.BudgetRepository;

import com.easy.budget.domain.BudgetManagement;
import com.easy.budget.dto.BudgetDto;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;

import java.util.NoSuchElementException;

@Service
public class BudgetService {
    
    private BudgetRepository budgetRepository;

    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaim("Id").toString();
    }
    
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<BudgetDto> getMyBudgets() {
        String memberId = getMemberId();
        return budgetRepository.findByMemberId(memberId)
                .stream()
                .map(b -> new BudgetDto(
                        b.getId(),
                        b.getMemberId(),
                        b.getBudget()
                ))
                .collect(Collectors.toList());
    }
    
    @Transactional
    public void addBudget(BudgetDto budgetDto) {
        String memberId = getMemberId();
        BudgetManagement budgetManagement = new BudgetManagement();
        budgetManagement.setMemberId(memberId);
        budgetManagement.setBudget(budgetDto.budget());
        budgetRepository.save(budgetManagement);
    }
    @Transactional
    public void updateBudget(BudgetDto budgetDto) {
        BudgetManagement budgetManagement = budgetRepository.findById(budgetDto.id())
                .orElseThrow(() -> new NoSuchElementException("Budget not found"));
        budgetManagement.setBudget(budgetDto.budget());
        budgetRepository.save(budgetManagement);
    }
    @Transactional
    public void deleteBudget(BudgetDto budgetDto) {
        String memberId = getMemberId();
        budgetRepository.deleteByIdAndMemberId(budgetDto.id(), memberId);
    }

}
