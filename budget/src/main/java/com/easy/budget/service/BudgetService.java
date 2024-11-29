package com.easy.budget.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.budget.repository.BudgetRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import com.easy.budget.domain.Budget;
import com.easy.budget.dto.Expense;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BudgetService {
    
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    private Mono<String> getMemberId() {
        return ReactiveSecurityContextHolder.getContext()
            .map(securityContext -> {
                Authentication authentication = securityContext.getAuthentication();
                if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
                    throw new IllegalStateException("Authentication not found or is not a JWT");
                }
                Jwt jwt = (Jwt) authentication.getPrincipal();
                Object usernameClaim = jwt.getClaim("username");
                if (usernameClaim == null) {
                    throw new IllegalStateException("Claim 'username' not found in JWT");
                }
                
                return jwt.getClaim("username").toString();
            });
    }
    
    @Transactional(readOnly = true)
    public Mono<List<Expense>> getMyBudgets() {
        return getMemberId()
            .publishOn(Schedulers.boundedElastic()) // 블로킹 호출을 위해 스케줄러 전환
            .flatMap(memberId -> Mono.just(
                budgetRepository.findAllByMemberId(memberId)
                    .stream()
                    .map(b -> new Expense(
                            b.getUsage(),
                            b.getAmount(),
                            b.getTime(),
                            b.getCurrency(),
                            b.getDate()
                    ))
                    .collect(Collectors.toList())
            ));
    }
    
    @Transactional
    public Mono<Void> addBudget(Expense budgetDto) {
        return getMemberId()
            .publishOn(Schedulers.boundedElastic()) // 블로킹 호출을 위해 스케줄러 전환
            .flatMap(memberId -> {
                Budget budgetManagement = new Budget();
                budgetManagement.setMemberId(memberId);
                budgetManagement.setUsage(budgetDto.usage());
                budgetManagement.setAmount(budgetDto.amount());
                budgetManagement.setTime(budgetDto.time());
                budgetManagement.setCurrency(budgetDto.currency());
                budgetManagement.setDate(budgetDto.date());
                budgetRepository.save(budgetManagement); // 블로킹 호출
                return Mono.empty();
            });
    }
    
    @Transactional
    public Mono<Void> updateBudget(List<Expense> budgetDtos) {
        return getMemberId()
            .publishOn(Schedulers.boundedElastic()) // 블로킹 호출을 위해 스케줄러 전환
            .flatMap(memberId -> {
                // 기존 예산 삭제
                budgetRepository.deleteByMemberId(memberId);
                
                // 새 예산 추가
                budgetDtos.forEach(budgetDto -> {
                    Budget budgetManagement = new Budget();
                    budgetManagement.setMemberId(memberId);
                    budgetManagement.setUsage(budgetDto.usage());
                    budgetManagement.setAmount(budgetDto.amount());
                    budgetManagement.setTime(budgetDto.time());
                    budgetManagement.setCurrency(budgetDto.currency());
                    budgetManagement.setDate(budgetDto.date());
                    budgetRepository.save(budgetManagement); // 블로킹 호출
                });
                return Mono.empty();
            });
    }
    
    @Transactional
    public Mono<Void> deleteBudget() {
        return getMemberId()
            .publishOn(Schedulers.boundedElastic()) // 블로킹 호출을 위해 스케줄러 전환
            .flatMap(memberId -> {
                budgetRepository.deleteByMemberId(memberId); // 블로킹 호출
                return Mono.empty();
            });
    }

}