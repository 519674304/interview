package com.wkk.api

import com.wkk.business.mapper.IDriverMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange

@RestController
@RequestMapping("/test")
class TestController {
    @Autowired
    IDriverMapper driverMapper

    @PostMapping("/hello")
    @GetExchange("")
    String hello() {
        driverMapper.queryDriverById(1)
        return "hello"
    }
}
