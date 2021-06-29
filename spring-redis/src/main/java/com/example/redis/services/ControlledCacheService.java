package com.example.redis.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ControlledCacheService {

	private static final String CONTROLLED_PREFIX = "myControlledPrefix_";

    public static String getCacheKey(String relevant){
        return CONTROLLED_PREFIX + relevant;
    }

    @Cacheable(cacheNames = "myControlledCache", key = "T(com.example.redis.services.ControlledCacheService).getCacheKey(#relevant)")
    public String getFromCache(String relevant) {
        return null;
    }

    @CachePut(cacheNames = "myControlledCache", key = "T(com.example.redis.services.ControlledCacheService).getCacheKey(#relevant)")
    public String populateCache(String relevant, String unrelevantTrackingId) {
        return "esto es todo de nuevo!";
    }

    @CacheEvict(cacheNames = "myControlledCache", key = "T(com.example.redis.services.ControlledCacheService).getCacheKey(#relevant)")
    public void removeFromCache(String relevant) {
    }

	
}
