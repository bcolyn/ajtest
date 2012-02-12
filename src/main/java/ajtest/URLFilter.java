package ajtest;

import java.net.URISyntaxException;
import java.net.URL;

public interface URLFilter {
    boolean accept(URL pathname) throws URISyntaxException;
}
