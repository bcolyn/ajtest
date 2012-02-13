package com.github.bcolyn.ajtest;

import org.testng.annotations.Test;

public class NoAOPTest {
    private DummyService dummyService = new DummyService();

    @Test
    public void shouldBeCalledWithoutAdvice() {
        dummyService.sayHello();
    }
}
