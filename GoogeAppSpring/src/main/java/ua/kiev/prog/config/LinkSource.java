package ua.kiev.prog.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by user on 10.09.2015.
 */
public class LinkSource {
    private static Map<String, String> links = new HashMap<>();

    static {
        Properties properties = new Properties();
        try {
            properties.load(LinkSource.class.getResourceAsStream("/links.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet())
                links.put((String) entry.getKey(), (String) entry.getValue());
        } catch (IOException ex) {
            System.out.println("Application initialization error. Check links properties.");
            ex.printStackTrace();
        }
    }

    public static String getLinkByCourse(String courseName) {
        return links.get(courseName);
    }
}
