package com.wkk.eureka.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@RequestMapping("/seeker")
interface StoreClient {

    @GetMapping("/hello")
    String getHello();

}