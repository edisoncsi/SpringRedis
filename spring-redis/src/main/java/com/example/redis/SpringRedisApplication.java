package com.example.redis;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.example.redis.services.CacheService;
import com.example.redis.services.ControlledCacheService;

@EnableCaching
@SpringBootApplication
public class SpringRedisApplication implements CommandLineRunner {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CacheService.class);
	
	@Autowired
	CacheService cacheService;
	
	@Autowired
	ControlledCacheService controlledCacheService;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

	@Override
    public void run(String... args) throws Exception {
        String firstString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
        log.info("Primero: {}", firstString);
        String secondString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
        log.info("Segundo: {}", secondString);
        String thirdString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
        log.info("Tercero: {}", thirdString);
        String fourthString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
        log.info("Cuarto: {}", fourthString);

        log.info("Iniciando caché controlado: -----------");
        String controlledFirst = getFromControlledCache("primero");
        log.info("Controlado primero: {}", controlledFirst);
        String controlledSecond = getFromControlledCache("segundo");
        log.info("Controlado segundo: {}", controlledSecond);

        getFromControlledCache("primero");
        getFromControlledCache("segundo");
        getFromControlledCache("tercero");
        //log.info("Clearing all cache entries:");
        //cacheService.forgetAboutThis("param1");
        //controlledCacheService.removeFromCache("controlledParam1");
    }

    private String getFromControlledCache(String param) {
        String fromCache = controlledCacheService.getFromCache(param);
        if (fromCache == null) {
            log.info("Oups: la caché estaba vacía. Yendo a poblarlo");
            String newValue = controlledCacheService.populateCache(param, UUID.randomUUID().toString());
            log.info("Poblando Cache with: {}", newValue);
            return newValue;
        }
        log.info("Retornando desde Cache: {}", fromCache);
        return fromCache;
    }

}
