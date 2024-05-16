package de.cyzetlc.roadsystem.utils.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class JSONWebReader {
    /**
     * Scanning website and tries to cast the content to a JSONObject
     *
     * @param url   the website url
     * @return      the phrased content
     */
    public static JSONObject getObjectFromUrl(String url) {
        try {
            InputStream is = new URL(url).openStream();
            InputStreamReader sr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader r = new BufferedReader(sr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
