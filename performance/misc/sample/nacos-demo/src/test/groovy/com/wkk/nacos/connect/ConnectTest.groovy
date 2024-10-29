package com.wkk.nacos.connect

import com.alibaba.nacos.api.NacosFactory
import com.alibaba.nacos.api.config.ConfigService
import com.alibaba.nacos.api.config.listener.Listener
import com.alibaba.nacos.api.naming.NamingService
import org.junit.jupiter.api.Test

import java.util.concurrent.Executor

class ConnectTest {
    def serverAddr = "192.168.247.142:8848"
    def dataId = "driver"
    def group = "default"

    def configService = NacosFactory.createConfigService(serverAddr)
    def namingService = NacosFactory.createNamingService(serverAddr)


    @Test
    void test8() {
        def isPublishOk = configService.publishConfig(dataId, group, "hello world")
        println isPublishOk
        def config = configService.getConfig(dataId, group, 5000)
        println config
    }

    @Test
    void test25(){
        def config = configService.getConfig(dataId, group, 5000)
        println config
    }

    @Test
    void test33(){
        configService.addListener(dataId, group, new Listener() {
            @Override
            Executor getExecutor() {
                return null
            }

            @Override
            void receiveConfigInfo(String configInfo) {
                println "receive:$configInfo"
            }
        })
        while (true){}
    }


}
