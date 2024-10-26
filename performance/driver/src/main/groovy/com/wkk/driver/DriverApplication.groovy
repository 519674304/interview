package com.wkk.driver

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@MapperScan("com.wkk.driver.business.infrastructure.mapper")
class DriverApplication {

  static void main(String[] args) {
    SpringApplication.run(DriverApplication, args)
  }

}
