package com.wkk.gateway.sample

import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerResponse

class SimpleGateway {
  @Bean
  RouterFunction<ServerResponse> getRoute() {
    RouterFunctions.route().GET("/get", HandlerFunctions.http("https://httpbin.org")).build();
  }
}
