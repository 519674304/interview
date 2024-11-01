package com.wkk.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableFeignClients
@EnableRetry
class CloudClientConfigApplication {

    static void main(String[] args) {
        SpringApplication.run(CloudClientConfigApplication, args)
    }
}
