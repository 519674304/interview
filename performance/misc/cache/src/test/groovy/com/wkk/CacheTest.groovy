package com.wkk

import cn.hutool.json.JSONUtil
import com.wkk.cache.PersonCache
import com.wkk.config.InitConfig
import com.wkk.model.Person
import io.lettuce.core.support.caching.RedisCache
import org.junit.jupiter.api.Test
import org.springframework.cache.Cache
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.core.RedisCallback
import org.springframework.data.redis.core.RedisTemplate

class CacheTest {

    @Test
    public void test6(){
        def context = new AnnotationConfigApplicationContext(InitConfig)
        def personCache = context.getBean(PersonCache)
        personCache.putPerson(new Person(id: 6L, name: "wkk", address: "address"))
        //def person = personCache.getPerson(4L)
        def person = personCache.getPerson(6L)
        println JSONUtil.toJsonStr(person)

    }


}
