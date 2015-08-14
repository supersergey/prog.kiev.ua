import java.io.InputStream;

/**
 * Created by sergey on 14.08.2015.
 */
public class Main implements Runnable {
    Thread thread;

    public Main() {
        thread = new Thread(this, "My Test Thread");
    }

    @Override
    public void run() {
        while (!Thread.interrupted())
        {
            System.out.println("Hello!");
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException ex) {}
        }
    }

    public static void main(String[] args)
    {
        Main m = new Main();
        m.thread.start();
    }
}
