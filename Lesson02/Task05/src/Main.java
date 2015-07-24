/* Task: parse json.txt file */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * Created by st on 23.07.2015.
 */
public class Main {

    public static void main(String[] args)
    {
        final String filename = "json.txt";

        Gson gson = new GsonBuilder().create();
        try
        {
           JSON json = (JSON) gson.fromJson(readJSON(filename), JSON.class);
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
        RandomAccessFile f = new RandomAccessFile("json.txt", "r");
        try {
            buf = new byte[(int)f.length()];
            f.read(buf);
        } finally {
            f.close();
        }
        return new String(buf);
    }

}
