package com.wkk.config

import io.lettuce.core.support.caching.RedisCache
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
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
    LettuceConnectionFactory redisConnectionFactory(){
        new LettuceConnectionFactory(new RedisStandaloneConfiguration("192.168.247.142", 6379))
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
