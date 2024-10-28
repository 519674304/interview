package com.wkk.driver.health.handler

import groovy.util.logging.Slf4j
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

@Slf4j
class PersonHandler {

    ServerResponse listPeople(ServerRequest req) {
        log.info("listPeople...")
        return ServerResponse.ok().build()
    }

    ServerResponse createPerson(ServerRequest request) {
        log.info("createPerson....")
        return ServerResponse.ok().build()
    }

    ServerResponse getPerson(ServerRequest request) {
        log.info("getPerson....")
        return ServerResponse.ok().build()
    }
}
