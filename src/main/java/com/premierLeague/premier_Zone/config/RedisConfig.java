package com.premierLeague.premier_Zone.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties properties){

        RedisStandaloneConfiguration cfg =new RedisStandaloneConfiguration();
        if(properties.getHost()!=null){
            cfg.setHostName(properties.getHost());
            cfg.setPort(properties.getPort());
            if(properties.getPassword()!=null && !properties.getPassword().isEmpty()){
                cfg.setPassword(RedisPassword.of(properties.getPassword()));
            }
        }
        return new LettuceConnectionFactory(cfg);

    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory connectionFactory){
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()))
                .disableCachingNullValues();

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        cacheConfigurationMap.put("player",defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurationMap.put("search",defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurationMap.put("team",defaultConfig.entryTtl(Duration.ofHours(1)));
        cacheConfigurationMap.put("nation",defaultConfig.entryTtl(Duration.ofHours(1)));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .transactionAware()
                .build();
    }
}
