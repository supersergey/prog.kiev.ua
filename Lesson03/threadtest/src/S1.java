/**
 * Created by st on 30.07.2015.
 */
public class S1 extends Thread {

    int x = 0;
    int y = 0;
    int addX() {x++; return x;}
    int addY() {y++; return y;}

    @Override
    public void run()
    {
        {
            for (int i = 0; i<10; i++)
                System.out.println(addX() + " " + addY());
        }
    }

    public static void main(String[] s)
    {
       /* Thread thread1 = new S1();
        Thread thread2 = new S1();
        thread1.start();
        thread2.start();*/

        int[][]x = new int[][] {new int[] {1,1}, new int[] {2,2}};
        int[][]y = new int[][] {new int[] {3,3}, new int[] {2,2}};
        System.out.println(y[0][0]);
        System.arraycopy(x, 0, y, 0, x.length);
        System.out.println(y[0][0]);

        String a = "test";
        String b = "test";
        String c = new String("test");
        System.out.println(a==b);
        System.out.println(a==c);


        int i = 1;
        switch (i)

    }

}
