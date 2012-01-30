package jagentloader;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({Listener.class})
public class DummyServiceTest {

    private DummyService dummyService = new DummyService();

    @BMRule(name = "test throw exception",
                targetClass = "benny.jagentloader.DummyService",
                targetMethod = "sayHello()",
                condition = "true",
                action = "throw new java.lang.IllegalStateException(\"by byteman\")")
    @Test(expectedExceptions = IllegalStateException.class)
    public void MyMethod() {
        dummyService.sayHello();
    }
}
