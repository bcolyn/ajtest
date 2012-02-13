AJTest
====================

Description
--------------------

AJTest is a library for running functional tests with AspectJ Load-Time Weaving and TestNG without a java agent.

Usage
--------------------

Just __extend from AspectJWeavingTest__

Alternatively, do the setup manually if you already have a base class:

* add `@AspectJTest` annotation to the class

* add a TestNG test listener: `@Listeners(ThreadContextClassloaderListener.class)` to the class


* configure an ObjectFactory:

-------------------------------
    @ObjectFactory
    public AspectJWeavingObjectFactory getFactory(){
        return AspectJWeavingObjectFactory.getInstance();
    }
------------------------------

Roadmap
--------------------
TODO