package ajtest;

import org.testng.annotations.Test;

public class BaseClassTest extends AspectJWeavingTest{

    private DummyService dummyService = new DummyService();

    @Test(expectedExceptions = IllegalStateException.class)
    public void interceptedMethod() {
        dummyService.sayHello();
    }
}
