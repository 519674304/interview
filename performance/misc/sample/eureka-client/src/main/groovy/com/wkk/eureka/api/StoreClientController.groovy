package com.wkk.eureka.api

import com.wkk.eureka.client.StoreClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreClientController implements StoreClient {
    @Override
    String getHello() {
        return "hello from 01"
    }
}
