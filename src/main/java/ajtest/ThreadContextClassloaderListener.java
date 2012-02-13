package ajtest;

import org.testng.IConfigurationListener2;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ThreadContextClassloaderListener implements IConfigurationListener2, ITestListener{

    public void beforeConfiguration(ITestResult tr) {
        AspectJWeavingObjectFactory.getInstance().setupThreadContextClassloader(tr.getTestClass().getRealClass());
    }

    public void onConfigurationSuccess(ITestResult itr) {
       AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onConfigurationFailure(ITestResult itr) {
       AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onConfigurationSkip(ITestResult itr) {
       AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestStart(ITestResult result) {
        AspectJWeavingObjectFactory.getInstance().setupThreadContextClassloader(result.getTestClass().getRealClass());
    }

    public void onTestSuccess(ITestResult result) {
        AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestFailure(ITestResult result) {
        AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestSkipped(ITestResult result) {
        AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        AspectJWeavingObjectFactory.getInstance().restoreThreadContextClassloader();
    }

    public void onStart(ITestContext context) {
        //
    }

    public void onFinish(ITestContext context) {
        //
    }
}
