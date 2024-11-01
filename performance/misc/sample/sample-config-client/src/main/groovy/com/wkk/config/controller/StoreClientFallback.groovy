package com.wkk.config.controller

import org.springframework.stereotype.Component


class StoreClientFallback implements StoreClient{
    @Override
    String getHello() {
        "problem occur"
    }
}
