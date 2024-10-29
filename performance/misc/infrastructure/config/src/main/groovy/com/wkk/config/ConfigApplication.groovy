package com.wkk.config

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfigApplication {

    static void main(String[] args) {
        SpringApplication.run(ConfigApplication, args)
    }

}
