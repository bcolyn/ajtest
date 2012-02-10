package ajtest;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Util {
    private Util() {
    }

    public static List<File> getClasspathFiles() {
        String cp = System.getProperty("java.class.path");
        List<File> result = new ArrayList<File>();
        for (StringTokenizer t = new StringTokenizer(cp, File.pathSeparator); t.hasMoreTokens(); ) {
            File f = new File(t.nextToken().trim());
            if (f.exists()) result.add(f);
        }
        return result;
    }

    public static List<File> filter(List<File> original, FileFilter filter){
        ArrayList<File> filtered = new ArrayList<File>();
        for (File file : original) {
            if (filter.accept(file)) filtered.add(file);
        }
        return filtered;
    }

    public static List<URL> filesToURLs(List<File> files) {
        List<URL> result = new ArrayList<URL>(files.size());
        for (File file : files) {
            try {
                result.add(file.toURI().toURL());
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Bad component in classpath: " + file.toString());
            }
        }
        return result;
    }
}
