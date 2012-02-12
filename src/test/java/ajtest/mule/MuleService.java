package ajtest.mule;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class MuleService implements Callable {

    public Object onCall(MuleEventContext eventContext) throws Exception {
        return "service";
    }
}
