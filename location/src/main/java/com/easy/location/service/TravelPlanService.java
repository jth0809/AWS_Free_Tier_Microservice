package com.easy.location.service;

import org.springframework.stereotype.Service;

import com.easy.location.repository.TravelPlanRepository;


import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.transaction.annotation.Transactional;

import com.easy.location.repository.TravelLocationRepository;
import org.springframework.security.oauth2.jwt.Jwt;

import com.easy.location.domain.TravelLocation;
import com.easy.location.domain.TravelPlan;
import com.easy.location.dto.Plandto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;


import java.util.List;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;


@Service
public class TravelPlanService {
    TravelPlanRepository travelPlanRepository;
    TravelLocationRepository travelLocationRepository;
    Logger logger = LoggerFactory.getLogger(TravelPlanService.class);
    
    public TravelPlanService(TravelPlanRepository travelPlanRepository, TravelLocationRepository travelLocationRepository) {
        this.travelPlanRepository = travelPlanRepository;
        this.travelLocationRepository = travelLocationRepository;
    }

    private Mono<String> getMemberId() {
        return ReactiveSecurityContextHolder.getContext()
        .map(securityContext -> {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication == null || !(authentication.getPrincipal() instanceof Jwt)) {
                throw new IllegalStateException("Authentication not found or is not a JWT");
            }
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Object nicknameClaim = jwt.getClaim("username");
            if (nicknameClaim == null) {
                throw new IllegalStateException("Claim 'nickname' not found in JWT");
            }
            
            return jwt.getClaim("username").toString();
        });
    }

    public Mono<List<Plandto>> getTravelPlans() {
        return getMemberId()
                .flatMap(memberId -> {

                    List<TravelPlan> plans = travelPlanRepository.findAllBymemberId(memberId);
                    
                    List<Plandto> plandtos = plans.stream()
                            .map(plan -> new Plandto(
                                    plan.getUuid(),
                                    plan.getMemberId(),
                                    plan.getTitle(),
                                    plan.getDescription(),
                                    plan.getStartDate(),
                                    plan.getEndDate(),
                                    plan.getImage(),
                                    plan.getTravelLocation()))
                            .collect(Collectors.toList());
                    return Mono.just(plandtos);
                    
                });
    }

    public Mono<TravelPlan> getTravelPlan(String id) {
        return getMemberId()
                .flatMap( memberId -> Mono.just(travelPlanRepository.findByUuidAndMemberId(id, memberId).orElseThrow(() -> new NoSuchElementException("Plan not found"))));
    }

    @Transactional
    public Mono<Void> addTravelPlan(Plandto plan) {
        return getMemberId()
            .flatMap(memberId -> {
                Mono<TravelLocation> travelLocationMono = Mono.just(plan.travelPlanLocation())
                        .flatMap(location -> {
                            return travelLocationRepository.findByTravelLocationName(location.getTravelLocationName())
                                    .map(Mono::just)
                                    .orElseGet(() -> Mono.just(travelLocationRepository.save(location)));
                        });
                return travelLocationMono.flatMap(travelLocation -> {
                    TravelPlan travelPlan = new TravelPlan();
                    travelPlan.setUuid(plan.id());
                    travelPlan.setMemberId(memberId);
                    travelPlan.setTitle(plan.title());
                    travelPlan.setDescription(plan.description());
                    travelPlan.setStartDate(plan.startDate());
                    travelPlan.setEndDate(plan.endDate());
                    travelPlan.setImage(plan.image());
                    travelPlan.setTravelLocation(travelLocation);
                    
                    return Mono.fromRunnable(() -> travelPlanRepository.save(travelPlan));
                });
            })
            .then();
    }

    @Transactional
    public Mono<Void> updateTravelPlan(Plandto plan) {
        return getMemberId()
            .flatMap(memberId -> Mono.fromCallable(() -> {
                // TravelPlan을 ID로 찾기
                TravelPlan travelPlan = travelPlanRepository.findByUuid(plan.id())
                    .orElseThrow(() -> new NoSuchElementException("Travel plan not found"));

                // 소유자 확인
                if (!memberId.equals(travelPlan.getMemberId())) {
                    throw new NoSuchElementException("Unauthorized");
                }

                // 업데이트할 필드가 있는지 확인하고 변경 적용
                boolean isUpdated = false;
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
                    // TravelLocation이 변경된 경우, 이름으로 영속화된 TravelLocation 조회 또는 저장
                    TravelLocation location = travelLocationRepository
                        .findByTravelLocationName(plan.travelPlanLocation().getTravelLocationName())
                        .orElse(plan.travelPlanLocation());
                    
                    // 조회된 TravelLocation을 설정
                    travelPlan.setTravelLocation(location);
                    isUpdated = true;
                }

                // 변경된 경우에만 저장
                if (isUpdated) {
                    travelPlanRepository.save(travelPlan);
                }
                return null;
            }).subscribeOn(Schedulers.boundedElastic()))
            .then();
    }


    @Transactional
    public Mono<Void> deleteTravelPlan(String id) {
        return getMemberId()
            .flatMap(memberId -> {
                return Mono.just(id)
                        .flatMap(planid -> travelPlanRepository.findByUuid(planid)
                                .map(Mono::just)
                                .orElseGet(Mono::empty))
                        .flatMap(travelPlan -> {
                            if (!memberId.equals(travelPlan.getMemberId())) {
                                throw new NoSuchElementException("unauthorized");
                            }
                            return Mono.fromRunnable(() -> travelPlanRepository.deleteByUuid(id));
                        });
            })
            .then();
    }
}
