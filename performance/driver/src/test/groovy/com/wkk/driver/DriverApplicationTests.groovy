package com.wkk.driver

import cn.hutool.json.JSONUtil
import com.wkk.unit.converter.UserDO
import com.wkk.unit.converter.UserPO
import org.junit.jupiter.api.Test
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@MapperScan("com.wkk.driver.business.infrastructure.mapper")
class DriverApplicationTests {

  @Test
  void contextLoads() {
  }

}
