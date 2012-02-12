package ajtest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

class Util {
    //private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    public static List<URL> getClasspathFiles(ClassLoader parent) {
//        if (true){
//        //if (parent instanceof URLClassLoader){
//            return Arrays.asList(((URLClassLoader) parent).getURLs());
//        }
        String cp = System.getProperty("java.class.path");
        String bcp = System.getProperty("sun.boot.class.path");
        cp = bcp + File.pathSeparatorChar + cp;
        for (Map.Entry<Object, Object> entry  : System.getProperties().entrySet()) {
            System.out.println(String.format("%s\t:\t%s", entry.getKey(), entry.getValue()));
        }
        List<URL> result = new ArrayList<URL>();
        for (StringTokenizer t = new StringTokenizer(cp, File.pathSeparator); t.hasMoreTokens(); ) {
            File f = new File(t.nextToken().trim());
            try{
                URL url = f.toURI().toURL();
                if (f.exists() && !result.contains(url)) {
                    result.add(url);
                }
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
