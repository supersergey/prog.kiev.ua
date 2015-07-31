import java.io.*;

/**
 * Created by user on 31.07.2015.
 */
public class Main {

    public static void main(String[] args) throws IOException
    {
        FileInputStream f = new FileInputStream(new File("c:\\temp\\index.html"));
        byte buf[] = new byte[f.available()];
        int length = f.read(buf);
        f.close();
        System.out.print(new String(buf));
        FileOutputStream fos = new FileOutputStream("c:\\temp\\result.txt");
        fos.write(buf);
        fos.flush();
        fos.close();
    }

}
