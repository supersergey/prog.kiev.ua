package ua.kiev.prog.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by user on 10.09.2015.
 */
public class LinkSource {
    private static HashMap<String, String> links;

    static  {
        BufferedReader br = new BufferedReader(new InputStreamReader(LinkSource.class.getResourceAsStream("/links.txt")));
        try {
            links = new HashMap<>();
            String entry;
            while (br.ready()) {
                entry = br.readLine();
                if (!entry.startsWith("//"))
                {
                    String[] parameters = entry.split("\\s=\\s");
                    links.put(parameters[0].trim(), parameters[1].trim());
                }
            }
        }
        catch (IOException ex)
        {
            System.out.println("Check links configuration.");
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
    public static String getLinkByCourse(String courseName)
    {
        return links.get(courseName);
    }
}
