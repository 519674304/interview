package com.wkk.config

import io.lettuce.core.support.caching.RedisCache
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisSentinelConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Configuration
@EnableCaching
@ComponentScan("com.wkk")
class InitConfig {

    @Bean
    LettuceConnectionFactory redisConnectionFactory() {
        def sentinel = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("192.168.247.142", 26379)
                .sentinel("192.168.247.142", 26380)
                .sentinel("192.168.247.142", 26381)
        new LettuceConnectionFactory(sentinel)
    }

    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        def template = new RedisTemplate<String, String>()
        template.setConnectionFactory(redisConnectionFactory)
        template
    }

    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .transactionAware()
                .withInitialCacheConfigurations(Collections.singletonMap("predefined",
                        RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()))
                .build();
    }

    //@Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        def template = new StringRedisTemplate()
        template.setConnectionFactory(redisConnectionFactory)
        template
    }

}
