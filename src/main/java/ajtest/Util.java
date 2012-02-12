package ajtest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Util {
    //private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    public static List<URL> getClasspathFiles(ClassLoader parent) {
        if (true){
        //if (parent instanceof URLClassLoader){
            return Arrays.asList(((URLClassLoader) parent).getURLs());
        }
        String cp = System.getProperty("java.class.path");
        List<URL> result = new ArrayList<URL>();
        for (StringTokenizer t = new StringTokenizer(cp, File.pathSeparator); t.hasMoreTokens(); ) {
            File f = new File(t.nextToken().trim());
            try{
                if (f.exists()) result.add(f.toURI().toURL());
            } catch (MalformedURLException ex){
                //LOGGER.error("Error parsing classpath urls");
            }
        }
        return result;
    }

    public static void dump(List<URL> classURLs){
        System.out.println("Classpath:");
        for (URL classURL : classURLs) {
            System.out.println(classURL);
        }
        System.out.println();
    }

    public static List<URL> filter(List<URL> original, URLFilter filter){
        ArrayList<URL> filtered = new ArrayList<URL>();
        for (URL file : original) {
            try {
                if (filter.accept(file)) filtered.add(file);
            } catch (URISyntaxException e) {
                //LOGGER.error("Error filtering",e);
            }
        }
        return filtered;
    }
}
