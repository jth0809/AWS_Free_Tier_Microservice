package com.easy.location.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.easy.location.domain.TravelLocation;
import com.easy.location.dto.Locationdto;
import com.easy.location.repository.TravelLocationRepository;

import org.springframework.transaction.annotation.Transactional;


@Service
public class LocationService {
    
    TravelLocationRepository travelLocationRepository;
    
    public LocationService(TravelLocationRepository travelLocationRepository) {
        this.travelLocationRepository = travelLocationRepository;
    }

    public TravelLocation getLocation(Locationdto location) {
        return travelLocationRepository.findById(location.id())
                .orElseThrow(() -> new NoSuchElementException("Location not found"));
    }

    @Transactional
    public void addLocation(Locationdto location) {
        TravelLocation travelLocation = new TravelLocation();
        travelLocation.setTravelLocationName(location.travelLocationName());
        travelLocation.setTravelLocationDescription(location.travelLocationDescription());
        travelLocation.setTravelLocationImage(location.travelLocationImage());
        travelLocation.setTravelLocationRating(location.travelLocationRating());
        travelLocation.setTravelLocationReview(location.travelLocationReview());
        travelLocation.setTravelLocation(location.travelLocation());
        travelLocationRepository.save(travelLocation);
    }

    @Transactional
    public void deleteLocation(Locationdto location) {
        travelLocationRepository.deleteById(location.id());
    }

    @Transactional
    public void updateLocation(Locationdto location) {
        TravelLocation travelLocation = travelLocationRepository.findById(location.id())
        .orElseThrow(() -> new NoSuchElementException("Location not found"));

        boolean isUpdated = false;

        if (location.travelLocationName() != null && !location.travelLocationName().equals(travelLocation.getTravelLocationName())) {
            travelLocation.setTravelLocationName(location.travelLocationName());
            isUpdated = true;
        }
        if (location.travelLocationDescription() != null && !location.travelLocationDescription().equals(travelLocation.getTravelLocationDescription())) {
            travelLocation.setTravelLocationDescription(location.travelLocationDescription());
            isUpdated = true;
        }
        if (location.travelLocationImage() != null && !location.travelLocationImage().equals(travelLocation.getTravelLocationImage())) {
            travelLocation.setTravelLocationImage(location.travelLocationImage());
            isUpdated = true;
        }
        if (location.travelLocationRating() != 0 && location.travelLocationRating() != travelLocation.getTravelLocationRating()) {
            travelLocation.setTravelLocationRating(location.travelLocationRating());
            isUpdated = true;
        }
        if (location.travelLocationReview() != null && !location.travelLocationReview().equals(travelLocation.getTravelLocationReview())) {
            travelLocation.setTravelLocationReview(location.travelLocationReview());
            isUpdated = true;
        }
        if (location.travelLocation() != null && !location.travelLocation().equals(travelLocation.getTravelLocation())) {
            travelLocation.setTravelLocation(location.travelLocation());
            isUpdated = true;
        }

        if (isUpdated) {
            travelLocationRepository.save(travelLocation);
        }
    }
}
