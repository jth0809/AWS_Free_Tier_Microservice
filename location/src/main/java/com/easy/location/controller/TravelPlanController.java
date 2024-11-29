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
import com.easy.location.service.TravelService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.easy.location.dto.MyItem;
import com.easy.location.dto.MyItemList;
import com.easy.location.dto.ReactivePlanMyItemList;
import com.easy.location.dto.PlanMyItemList;
import com.easy.location.dto.Plandto;
import com.easy.location.dto.Traveldto;
import com.easy.location.Adapter.TravelAdapter;
import com.easy.location.Adapter.TravelPlanAdapter;
import com.easy.location.domain.Location;
import com.easy.location.domain.TravelLocation;
import com.easy.location.dto.PlanList;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class TravelPlanController {
   
   private final TravelPlanService travelPlanService;
   private final TravelService travelService;

   public TravelPlanController(TravelPlanService travelPlanService, TravelService travelService) {
      this.travelPlanService = travelPlanService;
      this.travelService = travelService;
   }
        
   @GetMapping("/plan")
   public Mono<PlanMyItemList> getTravelPlans() {
      return travelService.getTravels().flatMap(travels -> {
         List<MyItemList> travelList = travels.stream()
            .map(TravelAdapter::toMyItemList)
            .collect(Collectors.toList());
         
         return travelPlanService.getTravelPlans().flatMap(plans -> {
            List<PlanList> planList = plans.stream()
               .map(TravelPlanAdapter::toPlanList)
               .collect(Collectors.toList());
            
            PlanMyItemList planMyItemList = new PlanMyItemList(travelList, planList);
            return Mono.just(planMyItemList);
         });
      });
   }
       
   @GetMapping("plan/{id}")
   public Mono<TravelPlan> getTravelPlan(@PathVariable String id) {
      return travelPlanService.getTravelPlan(id);
   }

   @PostMapping("/plan")
   public Mono<String> addPlan(@RequestBody PlanList plan) {
      return travelPlanService.addTravelPlan(TravelPlanAdapter.toPlandto(plan))
               .then(Mono.just("Plan added"));
   }

   @PutMapping("/plan")
   public void updatePlan(@RequestBody Plandto plan) {
      travelPlanService.updateTravelPlan(plan);
   }

   @DeleteMapping("/plan/{id}")
   public void deletePlan(@PathVariable String id) {
      travelPlanService.deleteTravelPlan(id);
   }
   @GetMapping("/plan/test")
   public Mono<String> testadd() {
      Location location = new Location(39.7,40.0);
      TravelLocation travelLocation = new TravelLocation("Test","Test", location,"Test", 100L,"Test");
      Plandto planDto = new Plandto("1", "Test", "Test", "Test", "Test", "Test","Test", travelLocation);
      return travelPlanService.addTravelPlan(planDto)
               .then(Mono.just("plan add Test Completed"));
   }

   @GetMapping("/plan/test1")
   public Mono<String> testadd2() {
      Traveldto traveldto = new Traveldto("travel_items", "Test", "Test", "Test", "Test", "Test",100L, "Test");
      return travelService.addTravel(traveldto).then(Mono.just("plan add Test Completed"));
   }

   @GetMapping("/plan/test2")
   public Mono<String> testupdate() {
      Location location = new Location(39.7,40.0);
      TravelLocation travelLocation = new TravelLocation("Test","Test", location,"Test", 100L,"Test");
      Plandto planDto = new Plandto("1", "1234", "1234", "1234", "Test", "Test", "test",travelLocation);
      return travelPlanService.updateTravelPlan(planDto)
               .then(Mono.just("plan update Test Completed"));
   }

   @GetMapping("/plan/test3")
   public Mono<String> testdelete() {
      return travelPlanService.deleteTravelPlan("1")
               .then(Mono.just("plan delete Test Completed"));
   }

       
}

       
