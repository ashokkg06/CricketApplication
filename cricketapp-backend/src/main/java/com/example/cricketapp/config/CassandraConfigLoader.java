package com.example.cricketapp.config;

import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
//@EnableCaching
public class CassandraConfigLoader {
    private static final DriverConfigLoader CONFIG_LOADER;

    static {
        CONFIG_LOADER = DriverConfigLoader.programmaticBuilder()
                .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(60))
                .build();
    }

    public static DriverConfigLoader getConfigLoader() {
        return CONFIG_LOADER;
    }

//    @Bean
//    public CacheManager cacheManager() {
////        return new ConcurrentMapCacheManager("matches");
//        SimpleCacheManager cacheManager = new SimpleCacheManager();
//        cacheManager.setCaches(Arrays.asList(
//                new ConcurrentMapCache("matches"),
//                new ConcurrentMapCache("match"),
//                new ConcurrentMapCache("info"),
//                new ConcurrentMapCache("battermatchstats"),
//                new ConcurrentMapCache("bowlermatchstats"),
//                new ConcurrentMapCache("extras")));
//        return cacheManager;
//    }
}

