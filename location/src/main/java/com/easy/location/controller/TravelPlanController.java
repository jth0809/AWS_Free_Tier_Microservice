package com.easy.location.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.easy.location.domain.TravelPlan;
import com.easy.location.service.TravelPlanService;
import com.easy.location.dto.Plandto;

import java.util.List;


@RestController
public class TravelPlanController {
        
        private final TravelPlanService travelPlanService;
        
        public TravelPlanController(TravelPlanService travelPlanService) {
            this.travelPlanService = travelPlanService;
        }
        
       @GetMapping("/plan")
       public List<TravelPlan> getTravelPlans() {
           return travelPlanService.getTravelPlans();
       }
       
       @GetMapping("plan/{id}")
       public TravelPlan getTravelPlan(@PathVariable Long id) {
           return travelPlanService.getTravelPlan(id);
       }

       @PostMapping("/plan")
       public void addPlan(@RequestBody Plandto plan) {
           travelPlanService.addTravelPlan(plan);
       }

       @PutMapping("/plan")
       public void updatePlan(@RequestBody Plandto plan) {
           travelPlanService.updateTravelPlan(plan);
       }

       @DeleteMapping("/plan/{id}")
       public void deletePlan(@PathVariable Long id) {
           travelPlanService.deleteTravelPlan(id);
       }

       
}

       
