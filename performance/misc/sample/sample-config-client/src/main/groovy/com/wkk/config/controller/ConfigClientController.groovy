package com.wkk.config.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

    @GetMapping("/getPort")
    String getPort(){
        return "port:${port},serverPort:${serverPort}"
    }

    @Test
    public void test25(){
        for (i in 0..<60) {
            if ((12 * i) % 60 == i) {
                println i
            }
        }
    }

}
