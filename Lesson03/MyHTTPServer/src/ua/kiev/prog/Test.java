package ua.kiev.prog;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by sergey on 03.08.2015.
 */
public class Test {
    public static void main(String[] args) throws IOException
    {

        byte[] z = new byte[] {(byte) 0x97, (byte) 0x98, (byte) 0x99, (byte) 0x9A};
        String s = new String(z, "UTF-8");
        FileOutputStream f1 = new FileOutputStream("c:\\temp\\f1.txt");
        FileOutputStream f2 = new FileOutputStream("c:\\temp\\f2.txt");
        f1.write(z);
        f2.write(s.getBytes("UTF-8"));
        f1.close();
        f2.close();
    }
}
