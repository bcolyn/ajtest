package ajtest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Util {
    private final static org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    public static List<URL> getClasspathFiles() {
        String cp = System.getProperty("java.class.path");
        String bcp = System.getProperty("sun.boot.class.path");
        cp = bcp + File.pathSeparatorChar + cp;
        List<URL> result = new ArrayList<URL>();
        for (StringTokenizer t = new StringTokenizer(cp, File.pathSeparator); t.hasMoreTokens(); ) {
            File f = new File(t.nextToken().trim());
            try{
                URL url = f.toURI().toURL();
                if (f.exists() && !result.contains(url)) {
                    result.add(url);
                }
            } catch (MalformedURLException ex){
                LOGGER.error("Error parsing classpath urls", ex);
            }
        }
        return result;
    }

    public static void dump(List<URL> classURLs){
        LOGGER.debug("Classpath:");
        for (URL classURL : classURLs) {
            LOGGER.debug(classURL.toString());
        }
        LOGGER.debug("---");
    }

}
