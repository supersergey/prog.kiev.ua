import java.net.URL;

/**
 * Created by st on 28.07.2015.
 */
public class Main {
    public static void main (String[] args) throws Exception
    {
        URL aURL = new URL("http://server.com:80/page?a=1&b=2");
        System.out.println(aURL.getProtocol());
        System.out.println(aURL.getHost());
        System.out.println(aURL.getPort());
        System.out.println(aURL.getPath());
        String[] params = aURL.getQuery().split("&");
        for (String s : params)
            System.out.println(s);
    }
}

