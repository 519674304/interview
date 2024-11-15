package com.wkk.mock

import org.junit.jupiter.api.Test
import org.mockito.Mockito

import static org.mockito.Mockito.*

class MockitoTest {

    @Test
    public void test6(){
        def mockedList = Mockito.mock(List)
        mockedList.add("one")
        mockedList.clear()
        verify(mockedList).add("one")
        verify(mockedList).clear()
    }

    @Test
    public void test20(){

    }
}
