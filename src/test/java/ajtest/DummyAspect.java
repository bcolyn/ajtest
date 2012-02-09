package ajtest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DummyAspect {

    @Around("execution(void DummyService.sayHello())")
    Object testAround(ProceedingJoinPoint pjp){
        throw new IllegalArgumentException("Test from aspectJ");
    }
}
