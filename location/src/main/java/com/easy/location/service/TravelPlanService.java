package com.easy.location.service;

import org.springframework.stereotype.Service;

import com.easy.location.repository.TravelPlanRepository;

import org.springframework.transaction.annotation.Transactional;

import com.easy.location.repository.TravelLocationRepository;
import org.springframework.security.oauth2.jwt.Jwt;

import com.easy.location.domain.TravelLocation;
import com.easy.location.domain.TravelPlan;
import com.easy.location.dto.Plandto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class TravelPlanService {
    TravelPlanRepository travelPlanRepository;
    TravelLocationRepository travelLocationRepository;
    
    public TravelPlanService(TravelPlanRepository travelPlanRepository) {
        this.travelPlanRepository = travelPlanRepository;
    }

    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaim("Id").toString();
    }

    public List<TravelPlan> getTravelPlans() {
        return travelPlanRepository.findAll();
    }

    public TravelPlan getTravelPlan(Long id) {
        return travelPlanRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Plan not found"));
    }

    @Transactional
    public void addTravelPlan(Plandto plan) {
        TravelLocation travelLocation = travelLocationRepository.findByTravelLocationName(plan.travelPlanLocation().getTravelLocationName())
        .orElse(travelLocationRepository.save(plan.travelPlanLocation()));
        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setMemberId(getMemberId());
        travelPlan.setTitle(plan.title());
        travelPlan.setDescription(plan.description());
        travelPlan.setStartDate(plan.startDate());
        travelPlan.setEndDate(plan.endDate());
        travelPlan.setImage(plan.image());
        travelPlan.setTravelLocation(travelLocation);
        travelPlanRepository.save(travelPlan);
    }

    @Transactional
    public void updateTravelPlan(Plandto plan){
        TravelPlan travelPlan = travelPlanRepository.findById(plan.id())
                .orElseThrow(() -> new NoSuchElementException("Plan not found"));
        boolean isUpdated = false;
        
        if (!getMemberId().equals(travelPlan.getMemberId())) {
            throw new NoSuchElementException("unauthorized");
        }
        
        if (plan.title() != null && !plan.title().equals(travelPlan.getTitle())) {
            travelPlan.setTitle(plan.title());
            isUpdated = true;
        }
        if (plan.description() != null && !plan.description().equals(travelPlan.getDescription())) {
            travelPlan.setDescription(plan.description());
            isUpdated = true;
        }
        if (plan.startDate() != null && !plan.startDate().equals(travelPlan.getStartDate())) {
            travelPlan.setStartDate(plan.startDate());
            isUpdated = true;
        }
        if (plan.endDate() != null && !plan.endDate().equals(travelPlan.getEndDate())) {
            travelPlan.setEndDate(plan.endDate());
            isUpdated = true;
        }
        if (plan.image() != null && !plan.image().equals(travelPlan.getImage())) {
            travelPlan.setImage(plan.image());
            isUpdated = true;
        }
        if (plan.travelPlanLocation() != null && !plan.travelPlanLocation().equals(travelPlan.getTravelLocation())) {
            travelPlan.setTravelLocation(plan.travelPlanLocation());
            isUpdated = true;
        }
        if (isUpdated) {
            travelPlanRepository.save(travelPlan);
        }
    }


    @Transactional
    public void deleteTravelPlan(Long id) {
        TravelPlan travelPlan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Plan not found"));
        
        if (!getMemberId().equals(travelPlan.getMemberId())) {
            throw new NoSuchElementException("unauthorized");
        }
        
        travelPlanRepository.deleteById(id);
    }
}
