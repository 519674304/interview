package com.wkk.driver.business.domain.repository

interface CacheRepository {

    String getCacheByKey(String key)

    void setCacheByKey(String key, String value)
}