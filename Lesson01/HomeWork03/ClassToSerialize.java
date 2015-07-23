package HomeWork03;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by st on 22.07.2015.
 */
public class ClassToSerialize {
    @Save
    public String s1 = "String #1";
    private String s2 = "String #2";
    @Save
    public static String s3 = "Static String";
    @Save
    public int i1 = 1;
    @Save
    public int i2 = 2;

    public void customSerialize(BufferedWriter br, ArrayList<String> values) throws IOException
    {
        for (String s : values)
        {
            br.write(s);
            br.write(System.getProperty("line.separator"));
        }
    }

    public void customDeserialize(ObjectOutput out, String [] values)
    {

    }

}
