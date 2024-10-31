package com.wkk.eureka

import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.DiscoveryClient
import com.netflix.discovery.EurekaClient
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class EurekaClientApplication {
    @Autowired
    EurekaClient discoveryClient

    @GetMapping("/")
    String home() {
        def list = discoveryClient.getNextServerFromEureka("", false)

        "Hello world"
    }

    static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication, args)
    }

}
