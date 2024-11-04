package com.wkk.unit

import org.junit.jupiter.api.Test

import java.util.concurrent.TimeUnit

class TrapTest {

    static void main(String[] args) {
        int num = 0
        while (true) {
            TimeUnit.SECONDS.sleep(1)
            println "num: $num"
            num += getRandomNumber()
        }
    }

    static def getRandomNumber() {
        return Math.random().intValue()
    }
    
    @Test
    public void test21(){
        concatMeh()
    }

    private void concatMeh() {
        def myTest = new MyTest()
        while (true) {
            TimeUnit.SECONDS.sleep(1)
            myTest.a += "mmma"
        }
    }

    class MyTest {
        def a = "abc"
    }
}
