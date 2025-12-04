package com.premierLeague.premier_Zone.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisStartupCheck {
    @Bean
    CommandLineRunner validateRedis(StringRedisTemplate redis) {
        return args -> {
            System.out.println(">>> REDIS STARTUP CHECK ...");
            try {
                redis.opsForValue().set("health-check", "ok");
                System.out.println(">>> REDIS OK");
            } catch (Exception ex) {
                System.out.println(">>> REDIS FAILED: " + ex.getClass().getName() + " -> " + ex.getMessage());
                ex.printStackTrace();
            }
        };
    }
}
