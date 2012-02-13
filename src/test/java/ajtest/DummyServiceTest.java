package ajtest;

import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@AspectJTest
public class DummyServiceTest {

    private DummyService dummyService = new DummyService();

    @Test(expectedExceptions = IllegalStateException.class)
    public void interceptedMethod() {
        dummyService.sayHello();
    }

    @Test
    public void testAnotherMethod(){
        int i = dummyService.getSomething();
        assertEquals(i, 2);
    }

    @ObjectFactory
    public WeavingFactory getFactory(){
        return WeavingFactory.getInstance();
    }
}
