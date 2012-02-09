package ajtest;

import org.aspectj.weaver.loadtime.WeavingURLClassLoader;
import org.testng.IObjectFactory2;

public class WeavingFactory implements IObjectFactory2 {

    public Object newInstance(Class<?> cls) {
        try {

            ClassLoader parent = cls.getClassLoader();
            //WeavingURLClassLoader weaver = new WeavingURLClassLoader(parent);
            WeavingURLClassLoader weaver = new WeavingURLClassLoader(ClassLoader.getSystemClassLoader());
            Thread.currentThread().setContextClassLoader(weaver);
            
            String name = cls.getCanonicalName();
            Class woven = Class.forName(name, false, weaver);
            Class wvn = weaver.loadClass(name);

            return woven.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
