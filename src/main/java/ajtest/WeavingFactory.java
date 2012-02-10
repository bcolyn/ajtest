package ajtest;

import org.testng.IObjectFactory2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class WeavingFactory implements IObjectFactory2 {

    private final AJTestClassLoader weaver;

    public WeavingFactory() {
        this(System.getProperty("java.class.path"));
    }

    /**
     * Limits search path for aspect to aspectPath
     * @param aspectPath - i.e. "target/test-classes" to only look there for aop.xml and aspects.
     */
    public WeavingFactory(String aspectPath){
        ClassLoader parent = ClassLoader.getSystemClassLoader();
        String cp = System.getProperty("java.class.path");

        final URL[] classPathElems = getURLs(cp);
        final URL[] aspectClassPathElems = getURLs(aspectPath);

        weaver = new AJTestClassLoader(classPathElems, aspectClassPathElems,parent);
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

    private static URL[] getURLs(String path) {
        List<URL> urlList = new ArrayList<URL>();
        for (StringTokenizer t = new StringTokenizer(path, File.pathSeparator); t.hasMoreTokens(); ) {
            File f = new File(t.nextToken().trim());
            try {
                if (f.exists()) {
                    URL url = f.toURI().toURL();
                    if (url != null)
                        urlList.add(url);
                }
            } catch (MalformedURLException e) {
                //skip bad parts
            }
        }

        URL[] urls = new URL[urlList.size()];
        urlList.toArray(urls);
        return urls;
    }

}
