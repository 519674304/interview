package com.wkk.order

import cn.hutool.json.JSONUtil
import org.junit.Test
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class JucTest {
  @Test
  void test5() {
    def locationMap = new ConcurrentHashMap<String, AtomicLong>()
    List<CompletableFuture<Void>> ts = []
    for (i in [1, 2, 3, 5]) {
      ts << CompletableFuture.runAsync (() -> {
        def m = [
                "a": 1,
                "b": 5,
                "c": 7,
                "d": 2,
        ]
        m.each {
          locationMap.computeIfAbsent(it.key, v -> new AtomicLong(0)).addAndGet(it.value)
        }
      })
    }
    CompletableFuture[] array = ts.toArray(new CompletableFuture[0])
    CompletableFuture.allOf(array).get()
    println JSONUtil.toJsonStr(locationMap)
  }

  @Test
  public void test35(){
    def executor = new ThreadPoolTaskExecutor()
    executor.setCorePoolSize(2)
    executor.setMaxPoolSize(4)
    executor.setQueueCapacity(20)
    executor.setThreadNamePrefix("test-")
    executor.initialize()
    executor.submit(() -> println 1 / 0)
    println "end"
    while (true);
  }
}
