package com.wkk.config.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigClientController {
    @Value('${port}')
    private String port

    @Value('${server.port}')
    private String serverPort

    @GetMapping("/getPort")
    String getPort(){
        return "port:${port},serverPort:${serverPort}"
    }

}
