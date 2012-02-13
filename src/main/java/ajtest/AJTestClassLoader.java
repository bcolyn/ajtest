package ajtest;

import org.aspectj.weaver.loadtime.WeavingURLClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class AJTestClassLoader extends WeavingURLClassLoader {
    private final static Logger LOGGER = LoggerFactory.getLogger(AJTestClassLoader.class);

    private final HashMap<String, Class> classesLoaded = new HashMap<String, Class>();
    private final Collection<String> excludes = new ArrayList<String>();


    public AJTestClassLoader(URL[] classURLs, URL[] aspectURLs, ClassLoader parent) {
        super(classURLs, aspectURLs, parent);
        excludes.add("java.");
        excludes.add("sun.");
        excludes.add("org.slf4j.");
    }

    /**
     * Override default class loading order. Try to load it ourselves by default,
     * unless shouldWeave indicates we shouldn't.
     *
     * @param name    name of the class to load
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        if (shouldWeave(name)) {
            if (classesLoaded.containsKey(name)) {
                return classesLoaded.get(name);
            } else {
                final Class wovenClass = findClass(name);
                classesLoaded.put(name, wovenClass);
                LOGGER.trace("loaded {}", name);
                return wovenClass;
            }
        } else {
            return super.loadClass(name, resolve);
        }
    }

    private boolean shouldWeave(String name) {
        for (String exclude : excludes) {
            if (name.startsWith(exclude)) return false;
        }
        return true;
    }
}
