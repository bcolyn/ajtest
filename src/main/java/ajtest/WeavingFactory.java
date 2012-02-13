package ajtest;

import org.testng.IObjectFactory2;

import java.net.URL;
import java.util.List;

import static ajtest.Util.*;

public final class WeavingFactory implements IObjectFactory2 {

    private final AJTestClassLoader weaver;
    private ClassLoader original;

    public WeavingFactory() {
        this(null);
    }

    /**
     * Limits search path for aspects for faster startup
     *
     * @param filter - filters the classpath used to find aspects
     */
    public WeavingFactory(URLFilter filter) {
        original = Thread.currentThread().getContextClassLoader();
        List<URL> classpathFiles = getClasspathFiles();
        dump(classpathFiles);
        List<URL> aspectClassPathElems = filter(classpathFiles, filter);
        weaver = new AJTestClassLoader(
                classpathFiles.toArray(new URL[classpathFiles.size()]),
                aspectClassPathElems.toArray(new URL[aspectClassPathElems.size()]),
                original);

    }

    public synchronized Object newInstance(Class<?> cls) {
        try {
            ClassLoader loader = getClassLoader(cls);
            Thread.currentThread().setContextClassLoader(loader);
            String name = cls.getCanonicalName();
            Class woven = Class.forName(name, false, loader);
            return woven.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ClassLoader getClassLoader(Class<?> cls) {
        if (shouldWeave(cls)) {
            return weaver;
        } else {
            return original;
        }
    }

    private boolean shouldWeave(Class<?> cls) {
        boolean found = false;
        while (cls != null) {
            if (cls.isAnnotationPresent(AspectJTest.class)) {
                found = true;
                break;
            } else {
                cls = cls.getSuperclass();
            }
        }
        return found;
    }
}
