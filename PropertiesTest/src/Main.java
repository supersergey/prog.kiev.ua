import java.io.InputStream;
import java.util.Properties;

/**
 * Created by user on 16.09.2015.
 */
public class Main {
    public static void main(String[] args) throws Exception
    {
        Properties properties = new Properties();
        InputStream is = Main.class.getResourceAsStream("test.properties");
        properties.load(is);
        System.out.println(properties.getProperty("ABC"));
    }
}
