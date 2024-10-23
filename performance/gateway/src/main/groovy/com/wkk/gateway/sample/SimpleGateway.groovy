package com.wkk.gateway.sample

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerResponse

@Configuration
class SimpleGateway {
  //@Bean
  RouterFunction<ServerResponse> getRoute() {
    RouterFunctions.route().GET("/get", HandlerFunctions.http("https://httpbin.org/")).build()
  }

  @Bean
  RouterFunction<ServerResponse> getSimpleRoute() {
    GatewayRouterFunctions.route("simple_route").GET ("/get", HandlerFunctions.http("https://httpbin.org/")).build()
  }
}
