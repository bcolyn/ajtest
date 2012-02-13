package com.github.bcolyn.ajtest;

import org.testng.annotations.Listeners;
import org.testng.annotations.ObjectFactory;

/**
 * Base class for user's tests. Sets marker annotation, adds listener, provides ObjectFactory.
 */
@AspectJTest
@Listeners(ThreadContextClassloaderListener.class)
public class AspectJWeavingTest {

    @ObjectFactory
    public AspectJWeavingObjectFactory getFactory(){
        return AspectJWeavingObjectFactory.getInstance();
    }
}
