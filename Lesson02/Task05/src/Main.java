import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by st on 23.07.2015.
 */
public class Main {

    public static void main(String[] args)
    {
        final String filename = "d:\\json.txt";

        final String jsonstring = "{\n" +
                "    \"name\": \"Vsevolod\",\n" +
                "    \"surname\": \"Ievgiienko\",\n" +
                "    \"phones\": [\"044-256-78-90\", \"066-123-45-67\"],\n" +
                "    \"sites\": [\"http://site1.com\", \"http://site2.com\"],\n" +
                "    \"address\": {\n" +
                "         \"country\": \"UA\",\n" +
                "         \"city\": \"Kyiv\",\n" +
                "         \"street\": \"abcd\"\n" +
                "    }\n" +
                "}\n";


        Gson gson = new GsonBuilder().create();
        try
        {
           JSON json = (JSON) gson.fromJson(jsonstring, JSON.class);
            System.out.println(json);
        }
        catch (Exception ignored)
        {
            ignored.printStackTrace();
        }
    }

    private static String readJSON(String filename) throws IOException
    {
        byte[] buf;
        RandomAccessFile f = new RandomAccessFile("d:\\json.txt", "r");
        try {
            buf = new byte[(int)f.length()];
            f.read(buf);
        } finally {
            f.close();
        }

        return new String(buf);
    }

}
