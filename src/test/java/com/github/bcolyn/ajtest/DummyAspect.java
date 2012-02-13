package com.github.bcolyn.ajtest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DummyAspect {

    @Around("execution(void com.github.bcolyn.ajtest.DummyService.sayHello())")
    public Object testAround(ProceedingJoinPoint pjp){
        throw new IllegalStateException("Test from aspectJ");
    }
}
