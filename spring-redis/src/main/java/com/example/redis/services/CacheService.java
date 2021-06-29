package com.example.redis.services;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service 
public class CacheService {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CacheService.class);

	@Cacheable(cacheNames = "myCache")
    public String cacheThis(){
		
        log.info("¡Regresando NO del caché!");

        return "this Is it";
    }
	
	@CacheEvict(cacheNames = "myCache")
	public void forgetAboutThis(){
	    log.info("¡Olvidando todo sobre esto!");
	}

	@Cacheable(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
	public String cacheThis(String relevant, String unrelevantTrackingId){
	    log.info("Volviendo NO de la caché. Seguimiento: {}!", unrelevantTrackingId);
	    return "eso es todo";
	}

	@CacheEvict(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
	public void forgetAboutThis(String relevant){
	    log.info("¡Olvidando todo sobre este '{}'!", relevant);
	}

	
}
