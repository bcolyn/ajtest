package jagentloader;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {
    public void onTestStart(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTestSuccess(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTestFailure(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTestSkipped(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onStart(ITestContext context) {
        try {
            Loader.loadAgent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onFinish(ITestContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
