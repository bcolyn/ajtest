package ajtest;

import org.testng.annotations.ObjectFactory;

@AspectJTest
public class AspectJWeavingTest {

    @ObjectFactory
    public WeavingFactory getFactory(){
        return new WeavingFactory();
    }
}
