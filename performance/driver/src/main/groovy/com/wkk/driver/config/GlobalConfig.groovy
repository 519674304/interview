package com.wkk.driver.config

import com.wkk.driver.business.handler.PersonHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.RequestPredicates
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.RouterFunctions
import org.springframework.web.servlet.function.ServerResponse

@Configuration
class GlobalConfig {

    @Bean
    RouterFunction<ServerResponse> getRoute() {
        def handler = new PersonHandler()
        RouterFunctions.route()
                .GET("/person/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getPerson)
                .GET("/person", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::listPeople)
                .POST("/person", handler::createPerson)
                .build()
    }
}
