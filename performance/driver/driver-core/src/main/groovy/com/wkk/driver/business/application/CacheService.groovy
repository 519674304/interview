package com.wkk.driver.business.application


import com.wkk.driver.business.domain.service.CacheDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CacheService {

    @Autowired
    CacheDomainService cacheDomainService

    def getByKey(String key) {
        cacheDomainService.getByKey(key)
    }

    def setValue(String key, String value) {
        cacheDomainService.setValue(key, value)
    }

}
