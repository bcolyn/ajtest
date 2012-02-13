package com.github.bcolyn.ajtest.mule;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mule.api.MuleEvent;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class MuleAspect {
    private AtomicInteger counter = new AtomicInteger(0);

    @Around(value = "execution(org.mule.api.MuleEvent org.mule.api.processor.MessageProcessor.process(org.mule.api.MuleEvent)) && args(event)")
    public Object aroundDispatch(ProceedingJoinPoint pjp, MuleEvent event) throws Throwable {
        counter.incrementAndGet();
        return pjp.proceed(pjp.getArgs());
    }
    
    public int getCount(){
        return counter.get();
    }
}
