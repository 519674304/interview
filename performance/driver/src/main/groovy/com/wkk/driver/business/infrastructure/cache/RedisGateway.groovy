package com.wkk.driver.business.infrastructure.cache

import com.wkk.driver.business.domain.repository.CacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisGateway implements CacheRepository {
    @Autowired
    StringRedisTemplate redisTemplate

    @Override
    String getCacheByKey(String key) {
        redisTemplate.opsForValue().get(key)
    }

    @Override
    void setCacheByKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value)
    }
}
