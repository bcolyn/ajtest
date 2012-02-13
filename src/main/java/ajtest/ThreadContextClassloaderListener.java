package ajtest;

import org.testng.IConfigurationListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ThreadContextClassloaderListener implements IConfigurationListener2, ITestListener{

    public void beforeConfiguration(ITestResult tr) {
        WeavingFactory.getInstance().setupThreadContextClassloader(tr.getTestClass().getRealClass());
    }

    public void onConfigurationSuccess(ITestResult itr) {
       WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onConfigurationFailure(ITestResult itr) {
       WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onConfigurationSkip(ITestResult itr) {
       WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestStart(ITestResult result) {
        WeavingFactory.getInstance().setupThreadContextClassloader(result.getTestClass().getRealClass());
    }

    public void onTestSuccess(ITestResult result) {
        WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestFailure(ITestResult result) {
        WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestSkipped(ITestResult result) {
        WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        WeavingFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onStart(ITestContext context) {
        //
    }

    public void onFinish(ITestContext context) {
        //
    }
}
