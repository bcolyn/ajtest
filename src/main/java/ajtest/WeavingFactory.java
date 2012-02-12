package ajtest;

import org.testng.IObjectFactory2;

import java.net.URL;
import java.util.List;

import static ajtest.Util.dump;
import static ajtest.Util.filter;
import static ajtest.Util.getClasspathFiles;

public final class WeavingFactory implements IObjectFactory2 {

    private final AJTestClassLoader weaver;

    public WeavingFactory() {
        this(null);
    }

    /**
     * Limits search path for aspects for faster startup
     * @param filter - filters the classpath used to find aspects
     */
    public WeavingFactory(URLFilter filter){
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        List<URL> classpathFiles = getClasspathFiles();
        dump(classpathFiles);
        List<URL> aspectClassPathElems = filter(classpathFiles, filter);

        weaver = new AJTestClassLoader(
                classpathFiles.toArray(new URL[classpathFiles.size()]),
                aspectClassPathElems.toArray(new URL[aspectClassPathElems.size()]),
                parent);
        Thread.currentThread().setContextClassLoader(weaver);
    }

    public Object newInstance(Class<?> cls) {
        try {
            String name = cls.getCanonicalName();
            Class woven = Class.forName(name, false, weaver);
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
