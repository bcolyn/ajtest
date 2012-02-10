package ajtest;

import org.testng.IObjectFactory2;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import static ajtest.Util.*;

public class WeavingFactory implements IObjectFactory2 {

    private final AJTestClassLoader weaver;

    public WeavingFactory() {
        this(null);
    }

    /**
     * Limits search path for aspects for faster startup
     * @param filter - filters the classpath used to find aspects
     */
    public WeavingFactory(FileFilter filter){
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        List<File> classpathFiles = getClasspathFiles();
        List<File> aspectClassPathElems = filter(classpathFiles, filter);

        weaver = new AJTestClassLoader(filesToURLs(classpathFiles), filesToURLs(aspectClassPathElems),parent);
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
