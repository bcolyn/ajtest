package ajtest;

import org.aspectj.weaver.loadtime.WeavingURLClassLoader;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;

public class AJTestClassLoader extends WeavingURLClassLoader {
    private final HashMap<String, Class> classesLoaded = new HashMap<String, Class>();

    public AJTestClassLoader(URL[] classURLs, URL[] aspectURLs, ClassLoader parent) {
        super(classURLs, aspectURLs, parent);
    }

    /**
     * Override default class loading order. Try to load it ourselves by default,
     * unless shouldWeave indicates we shouldn't.
     *
     * @param name name of the class to load
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (shouldWeave(name)) {
            synchronized (classesLoaded) {
                if (classesLoaded.containsKey(name)) {
                    return classesLoaded.get(name);
                } else {
                    final Class wovenClass = findClass(name);
                    classesLoaded.put(name, wovenClass);
                    return wovenClass;
                }
            }
        } else {
            return super.loadClass(name, resolve);
        }
    }

    @Override
    protected Class defineClass(String name, byte[] b, CodeSource cs) throws IOException {
        return super.defineClass(name, b, cs);
    }

    private boolean shouldWeave(String name) {
        return !name.startsWith("java.");
    }
}
