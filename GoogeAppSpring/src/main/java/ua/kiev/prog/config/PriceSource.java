package ua.kiev.prog.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by user on 10.09.2015.
 */
public class PriceSource {
    private static Map<String, String> prices = new HashMap<>();

    static {
        Properties properties = new Properties();
        try {
            properties.load(PriceSource.class.getResourceAsStream("/prices.properties"));
            for (Map.Entry<Object, Object> entry : properties.entrySet())
                prices.put((String) entry.getKey(), (String) entry.getValue());
        } catch (IOException ex) {
            System.out.println("Application initialization error. Check prices properties.");
            ex.printStackTrace();
        }
    }

    public static String getPriceByCourse(String courseName)
    {
        return prices.get(courseName);
    }
}
