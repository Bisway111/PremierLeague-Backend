package com.premierLeague.premier_Zone.config;

import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.OFF, basePackages = {})
public class DisableRedisRepositories {
}
