package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by user on 10.09.2015.
 */
public class PriceSource {
    private static HashMap<String, String> prices;

    static  {
        BufferedReader br = new BufferedReader(new InputStreamReader(LinkSource.class.getResourceAsStream("/prices.txt")));
        try {
            prices = new HashMap<>();
            String entry;
            while (br.ready()) {
                entry = br.readLine();
                if (!entry.startsWith("//"))
                {
                    String[] parameters = entry.split("\\s=\\s");
                    prices.put(parameters[0].trim(), parameters[1].trim());
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println("Check prices configuration.");
            ex.printStackTrace();
        }
        finally {
            try
            {
                br.close();
            }
            catch (IOException ignored) {}
        }
    }

    public static String getPriceByCourse(String courseName)
    {
        return prices.get(courseName);
    }
}
