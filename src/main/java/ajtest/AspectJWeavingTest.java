package ajtest;

import org.testng.annotations.Listeners;
import org.testng.annotations.ObjectFactory;

@AspectJTest
@Listeners(ThreadContextClassloaderListener.class)
public class AspectJWeavingTest {

    @ObjectFactory
    public WeavingFactory getFactory(){
        return WeavingFactory.getInstance();
    }
}
