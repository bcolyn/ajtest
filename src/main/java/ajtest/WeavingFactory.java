package ajtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IObjectFactory2;

import java.net.URL;
import java.util.List;

import static ajtest.Util.dump;
import static ajtest.Util.getClasspathFiles;

public final class WeavingFactory implements IObjectFactory2 {
    private final static Logger LOGGER = LoggerFactory.getLogger(WeavingFactory.class);
    private final static WeavingFactory INSTANCE = new WeavingFactory();
    private final AJTestClassLoader weaver;
    private ClassLoader original;

    private WeavingFactory() {
        original = Thread.currentThread().getContextClassLoader();
        List<URL> classpathFiles = getClasspathFiles();
        dump(classpathFiles);
        weaver = new AJTestClassLoader(
                classpathFiles.toArray(new URL[classpathFiles.size()]),
                classpathFiles.toArray(new URL[classpathFiles.size()]),
                original);

    }

    public static WeavingFactory getInstance() {
        return INSTANCE;
    }

    public synchronized Object newInstance(Class<?> cls) {
        try {
            LOGGER.debug("Instantiating class: {}", cls);
            ClassLoader loader = setupThreadContextClassloader(cls);
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

    public ClassLoader setupThreadContextClassloader(Class realClass) {
        ClassLoader loader = getClassLoader(realClass);
        Thread.currentThread().setContextClassLoader(loader);
        LOGGER.debug("Set context classloader: {}", Thread.currentThread().getContextClassLoader());
        return loader;
    }

    public void restoreThreadContextClassloader() {
        Thread.currentThread().setContextClassLoader(original);
        LOGGER.debug("Restored context classloader: {}", Thread.currentThread().getContextClassLoader());
    }
}
