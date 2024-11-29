package com.easy.location.controller;

import org.springframework.web.bind.annotation.RestController;

import com.easy.location.Adapter.TravelAdapter;
import com.easy.location.domain.Travel;
import com.easy.location.dto.MyItem;
import com.easy.location.dto.Traveldto;
import com.easy.location.service.TravelService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
public class TravelController {
    TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/travel")
    public Mono<String> addPlan(@RequestBody List<MyItem> travel) {
       return Flux.fromIterable(travel)
             .map(TravelAdapter::myItemToTraveldto)
                .flatMap(travelService::addTravel)
             .then(Mono.just("Travel added")); 
      /*
      plan.planList().forEach(planItem -> {
         Plandto plandto = TravelPlanAdapter.toPlandto(planItem);
         travelPlanService.addTravelPlan(plandto).then(Mono.just("Plan added"));
      });
      */
    }
    @DeleteMapping("/travel/{uid}")
    public Mono<String> deleteTravel(@PathVariable String uid){
        return travelService.deleteTravel(uid).then(Mono.just("Travel deleted"));
    }

/* 
    @GetMapping("/travel")
    public Mono<List<Traveldto>> getMyTravels() {
        return Mono.fromFuture(travelService.getTravels());
    }
    */
    /* 
    @GetMapping("/travel/{travelId}")
    public Travel getTravel(@PathVariable Long travelId) {
        return travelService.getTravel(new Traveldto(travelId, null, null, null, null, null, null, null));
    }

    @PostMapping("/travel")
    public String addTravel(@RequestBody Traveldto traveldto) {
        travelService.addTravel(traveldto);
        return "Travel added";
    }

    @PutMapping("/travel")
    public String updateTravel(@RequestBody Traveldto traveldto) {
        travelService.updateTravel(traveldto);
        return "Travel updated";
    }

    @DeleteMapping("/travel")
    public String deleteTravel(@RequestBody Traveldto traveldto) {
        travelService.deleteTravel(traveldto);
        return "Travel deleted";
    }
    

    @GetMapping("/travel/test")
    public String testadd() {
        travelService.addTravel(new Traveldto(1L, "test", "test", "test", "test", "test", "test", "test"));
        return "is working";
    }
        */

    
    
}
