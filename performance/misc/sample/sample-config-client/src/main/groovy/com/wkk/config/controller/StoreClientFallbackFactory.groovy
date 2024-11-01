package com.wkk.config.controller

import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class StoreClientFallbackFactory implements FallbackFactory<StoreClientFallback> {

    @Override
    StoreClientFallback create(Throwable cause) {
        return new StoreClientFallback()
    }
}
