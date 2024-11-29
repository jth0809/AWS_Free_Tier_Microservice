package com.easy.location.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easy.location.domain.Travel;
import com.easy.location.dto.Traveldto;
import com.easy.location.repository.TravelRepository;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TravelService {
    TravelRepository travelRepository;
    Logger logger = LoggerFactory.getLogger(TravelService.class);

    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public Mono<String> getMemberId() {
        return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
                throw new IllegalStateException("Authentication not found or is not a JWT");
            }
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaim("username").toString();
        });
    }
    
    @Transactional
    public Mono<List<Traveldto>> getTravels() {
        return getMemberId()
            .flatMap(memberId -> {
                List<Travel> travels = travelRepository.findAllBymemberId(memberId);
                
                List<Traveldto> travelDtos = travels.stream()
                        .map(travel -> new Traveldto(
                                travel.getId(),
                                travel.getMemberId(),
                                travel.getTitle(),
                                travel.getLocation(),
                                travel.getStartDate(),
                                travel.getEndDate(),
                                travel.getImage(),
                                travel.getStatus()
                        ))
                        .collect(Collectors.toList());
                logger.info("TravelDtos: {}", travelDtos);
                return Mono.just(travelDtos);
            });
    }
/*
    @Transactional
    public Mono<Traveldto> getTravel(Traveldto travelDto) {
        return getMemberId()
            .flatMap(memberId -> {
                Travel travel = travelRepository.findByIdAndMemberId(travelDto.id(), memberId)
                        .orElseThrow(() -> new NoSuchElementException("No travel found with id: " + travelDto.id()));
                
            });
    }
*/
    @Transactional
    public Mono<Void> addTravel(Traveldto travelDto) {
        return getMemberId()
            .flatMap(memberId -> {
                Travel travel = new Travel();
                travel.setId(travelDto.id());
                travel.setMemberId(memberId);
                travel.setTitle(travelDto.title());
                travel.setLocation(travelDto.location());
                travel.setStartDate(travelDto.startDate());
                travel.setEndDate(travelDto.endDate());
                travel.setImage(travelDto.image());
                travel.setStatus(travelDto.status());
                
                return Mono.fromRunnable(() -> travelRepository.save(travel));
            })
            .then();
    }

    @Transactional
    public Mono<Void> updateTravel(Traveldto travelDto) {
        return getMemberId()
                .flatMap(memberId -> {
                    Travel travel = travelRepository.findById(travelDto.id())
                            .orElseThrow(() -> new NoSuchElementException("No travel found with id: " + travelDto.id()));

                    if(!travel.getMemberId().equals(memberId)) {
                        throw new NoSuchElementException("Travel with id: " + travelDto.id() + " does not belong to member: " + memberId);
                    }

                    travel.setTitle(travelDto.title());
                    travel.setLocation(travelDto.location());
                    travel.setStartDate(travelDto.startDate());
                    travel.setEndDate(travelDto.endDate());
                    travel.setImage(travelDto.image());
                    travel.setStatus(travelDto.status());

                    return Mono.fromRunnable(() -> travelRepository.save(travel));
                })
                .then();
    }

    @Transactional
    public Mono<Void> deleteTravel(String id) {
        return getMemberId()
                .flatMap(memberId -> {
                    Travel travel = travelRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("No travel found with id: " + id));

                    if(!travel.getMemberId().equals(memberId)) {
                        throw new NoSuchElementException("Travel with id: " + id + " does not belong to member: " + memberId);
                    }

                    return Mono.fromRunnable(() -> travelRepository.delete(travel));
                })
                .then();
    }
}
