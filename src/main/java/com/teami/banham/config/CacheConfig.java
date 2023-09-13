package com.teami.banham.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    @Primary
    public CacheManager serviceListCacheManager() {
        return new ConcurrentMapCacheManager("serviceList");
    }

    @Bean
    public CacheManager foodListCacheManager() {
        return new ConcurrentMapCacheManager("foodList");
    }

    @Bean
    public CacheManager hotelListCacheManager() {
        return new ConcurrentMapCacheManager("hotelList");
    }

    @Bean
    public CacheManager medicalListCacheManager() {
        return new ConcurrentMapCacheManager("medicalList");
    }

    @Bean
    public CacheManager shoppingListCacheManager() {
        return new ConcurrentMapCacheManager("shoppingList");
    }

    @Bean
    public CacheManager travelListCacheManager() {
        return new ConcurrentMapCacheManager("travelList");
    }
}
