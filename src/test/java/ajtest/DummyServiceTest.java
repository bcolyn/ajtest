package ajtest;

import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

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
        return new WeavingFactory(new URLFilter(){
            public boolean accept(URL pathname) throws URISyntaxException {
                File f = new File(pathname.toURI());
                return f.isDirectory() && f.getName().endsWith("test-classes");
            }
        });
    }
}
