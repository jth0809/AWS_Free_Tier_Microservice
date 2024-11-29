package com.cache.service.repository;

import org.springframework.data.repository.reactive.reactiveRedisRepository;



public interface CacheRepository extends reactiveRedisRepository<Cache, String> {
}
