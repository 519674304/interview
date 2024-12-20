package com.wkk.config.controller

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient(value = "seeker", path = "seeker")
interface StoreClient {

    @GetMapping("/hello")
    String getHello();

}