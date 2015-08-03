import java.net.URL;
import java.util.Date;

/**
 * Created by st on 28.07.2015.
 */
public class Main {

        public static void main (String[] args) throws Exception
        {
            final Date f = new Date();
            class X
            {
                public void m()
                {
                    f.setTime(1L);
                }
            }
            X x = new X();
            x.m();
            System.out.println(f);
        }
    }

