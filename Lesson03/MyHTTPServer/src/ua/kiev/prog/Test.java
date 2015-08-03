package ua.kiev.prog;

import java.io.*;

/**
 * Created by sergey on 03.08.2015.
 */
public class Test {
    public static void main(String[] args) throws IOException
    {
        final String folder = "c:\\temp\\";
        final String filename = "DSC_7714.JPG";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = new FileInputStream(folder + filename);
        byte buf[] = new byte[1024 * 1024];
        int r;
        while (is.available()>0)
        {
            r = is.read(buf);
            if (r>0)
                baos.write(buf, 0, r);
        }
        FileOutputStream fos = new FileOutputStream(folder + "temp" + filename);
        String request = new String(baos.toByteArray());
        System.out.println(request.length());
        fos.write(baos.toByteArray());
        fos.flush();
        fos.close();
    }
}
