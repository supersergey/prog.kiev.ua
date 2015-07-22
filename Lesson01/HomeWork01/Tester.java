/**
 * Created by st on 21.07.2015.
 */
@SuppressWarnings("all")

public class Tester {
    @Test(a = 5, b = 20)
    public static void printValues(int a, int b) {
        System.out.println(a);
        System.out.println(b);
        System.out.println("a + b = " + (a + b));
    }
}
