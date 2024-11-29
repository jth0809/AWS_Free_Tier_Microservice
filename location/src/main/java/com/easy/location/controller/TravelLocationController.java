package com.easy.location.controller;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import com.easy.location.dto.Locationdto;
import com.easy.location.service.LocationService;

public class TravelLocationController {

    LocationService locationService;

    public TravelLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/location")
    public void addLocation(@RequestBody Locationdto location) {
        locationService.addLocation(location);
    }

    @PutMapping("/location")
    public void updateLocation(@RequestBody Locationdto location) {
        locationService.updateLocation(location);
    }

    @DeleteMapping("/location/{id}")
    public void deleteLocation(@RequestBody Locationdto location) {
        locationService.deleteLocation(location);
    }
    
}
