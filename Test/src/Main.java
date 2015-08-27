/**
 * Created by user on 25.08.2015.
 */
public class Main {

    public static void main(String[] args)
    {
      A b = new B();
        ((B) b).m();

        C c = new C();
        c.x = 1;

    }
}

interface A
{}

class B implements A
{
    public int x;
    public void m() {}
}

class C extends B
{
    private int x;
}
