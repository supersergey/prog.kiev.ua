package HomeWork02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by st on 22.07.2015.
 */
@SaveTo(filename = "c:\\file.txt")
public class TextContainer {
    public String aString = "prog.kiev.ua homework";

    @Saver
    public void save(String filename)
    {
        try
        {
            BufferedWriter br = new BufferedWriter(new FileWriter(filename));
            br.write(aString);
            br.close();
        }
        catch (IOException e)
        {
            System.out.println("Wrong filename.");
        }

    }
}
