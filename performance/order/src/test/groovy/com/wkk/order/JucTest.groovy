package com.wkk.order

import cn.hutool.json.JSONUtil
import org.junit.Test

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicLong

class JucTest {
  @Test
  void test5() {
    def locationMap = new ConcurrentHashMap<String, AtomicLong>()
    List<CompletableFuture<Void>> ts = []
    for (i in 0..<100000) {
      ts << CompletableFuture.runAsync (() -> {
        def m = [
                "a": 1,
                "b": 5,
                "c": 7,
                "d": 2
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
}
