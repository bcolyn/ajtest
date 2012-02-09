package ajtest;

import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

public class DummyServiceTest {

    private DummyService dummyService = new DummyService();

    @Test(expectedExceptions = IllegalStateException.class)
    public void MyMethod() {
        dummyService.sayHello();
    }

    @ObjectFactory
    public WeavingFactory getFactory(){
        return new WeavingFactory();
    }
}
