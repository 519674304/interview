package com.wkk.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/driver/health")
class HealthController {

  @GetMapping("/hello")
  def hello() {
    "hello from order"
  }

}
