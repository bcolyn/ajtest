package ajtest.mule;

import ajtest.AspectJTest;
import ajtest.WeavingFactory;
import org.aspectj.lang.Aspects;
import org.mule.DefaultMuleMessage;
import org.mule.MuleServer;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

@AspectJTest
public class MuleTest {
    private MuleClient client;
    private MuleServer server;

    @BeforeClass
    public void startMule() throws MuleException {
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
        return new WeavingFactory();
    }
}
