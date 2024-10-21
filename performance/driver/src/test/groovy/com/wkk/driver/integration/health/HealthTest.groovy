package com.wkk.driver.integration.health

import com.wkk.driver.controller.HealthController
import com.wkk.util.DynamicProxyUtil
import org.junit.Test

class HealthTest {

  @Test
  void test6(){
    def healthController = DynamicProxyUtil.createProxy(HealthController.class)
    println healthController.hello()
  }
}
