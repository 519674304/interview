package com.wkk.driver.business.domain.service

import com.wkk.driver.business.domain.repository.CacheRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CacheDomainService {

    @Autowired
    CacheRepository cacheRepository

    def getByKey(String key) {
        cacheRepository.getCacheByKey(key)
    }

    def setValue(String key, String value) {
        cacheRepository.setCacheByKey(key, value)
    }

}
