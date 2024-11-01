package com.wkk.config.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.config.client.ConfigClientProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.context.refresh.ContextRefresher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RefreshScope
class ConfigClientController {
    @Value('${port}')
    private String port

    @Value('${server.port}')
    private String serverPort

    @Autowired
    private StoreClient storeClient


    @GetMapping("/getPort")
    String getPort() {
        return "port:$port,serverPort:$serverPort"
    }

    @GetMapping("/remote/hello")
    String remoteHello() {
        return storeClient.getHello()
    }

}
