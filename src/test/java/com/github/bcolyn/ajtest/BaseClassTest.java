package com.github.bcolyn.ajtest;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ThreadContextClassloaderListener.class)
public class BaseClassTest extends AspectJWeavingTest {

    private DummyService dummyService = new DummyService();

    @Test(expectedExceptions = IllegalStateException.class)
    public void interceptedMethod() {
        dummyService.sayHello();
    }
}
