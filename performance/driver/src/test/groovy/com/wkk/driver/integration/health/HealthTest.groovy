package com.wkk.driver.integration.health

import com.wkk.driver.health.controller.HealthController
import com.wkk.util.DynamicProxyUtil
import org.junit.Test

class HealthTest {
  def healthController = DynamicProxyUtil.createProxy(HealthController.class)

  @Test
  void test6(){
    println healthController.hello()
  }

  @Test
  public void test16(){

  }


}
