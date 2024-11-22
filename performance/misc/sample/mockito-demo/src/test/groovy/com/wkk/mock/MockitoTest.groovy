package com.wkk.mock

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import static org.mockito.Mockito.*

class MockitoTest {
    @Mock
    List<String> stringList

    private AutoCloseable closeable

    @BeforeEach
    void init() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @AfterEach
    void releaseMocks() {
        closeable.close()
    }

    @Test
    public void test6() {
        def mockedList = Mockito.mock(List)
        mockedList.add("one")
        mockedList.clear()
        verify(mockedList).add("one")
        verify(mockedList).clear()
    }

    @Test
    void test20() {
        stringList.add("one")
        stringList.clear()
        verify(stringList).add("one")
        verify(stringList).clear()
    }
}
