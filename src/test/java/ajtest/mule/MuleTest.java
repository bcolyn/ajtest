package ajtest.mule;

import ajtest.AspectJTest;
import ajtest.ThreadContextClassloaderListener;
import ajtest.WeavingFactory;
import org.aspectj.lang.Aspects;
import org.mule.DefaultMuleMessage;
import org.mule.MuleServer;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

@AspectJTest
@Listeners(ThreadContextClassloaderListener.class)
public class MuleTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(MuleTest.class);

    private MuleClient client;
    private MuleServer server;

    @BeforeClass
    public void startMule() throws MuleException {
        LOGGER.debug("Thread context classloader: {}", Thread.currentThread().getContextClassLoader());
        server = new MuleServer("mule-test.xml");
        server.start(false, false);
        client = new MuleClient(server.getMuleContext());
    }

    @Test(groups = "mule")
    public void sendMessage() throws Exception {
        DefaultMuleMessage request = new DefaultMuleMessage("message", server.getMuleContext());
        MuleMessage response = client.send("vm://q.in", request);
        assertEquals(response.getPayloadAsString(), "service");
        MuleAspect aspect = Aspects.aspectOf(MuleAspect.class);
        assertThat(aspect.getCount()).isGreaterThan(0);
    }

    @AfterClass(alwaysRun = true)
    public void stopMule() throws MuleException {
        client.dispose();
        server.getMuleContext().stop();
    }

    @ObjectFactory
    public WeavingFactory getFactory() {
        return WeavingFactory.getInstance();
    }
}
