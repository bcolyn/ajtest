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

Maven
--------------------

Snapshot binaries are available from Sonatype OSS Snapshot repository

--------------------
<dependency>
    <groupId>com.github.bcolyn.ajtest</groupId>
    <artifactId>ajtest</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
 ...
<repositories>
    <repository>
        <id>sonatype-oss-snapshots</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
--------------------

Releases will be made available from Central when they are ready.

Roadmap
--------------------
TODO