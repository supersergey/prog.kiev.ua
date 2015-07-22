import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by st on 21.07.2015.
 */
public class Main {

    public static void main(String[] s) {
        Method[] myMethods = Tester.class.getDeclaredMethods();

        for (Method mth : myMethods) {
            if (mth.getName().equals("printValues"))
                try {
                    Annotation[] annotations = mth.getAnnotations();
                    for (Annotation ann : annotations)
                        if (ann instanceof Test) {
                            mth.invoke(Tester.class, ((Test) ann).a(), ((Test) ann).b());
                        }
                } catch (Exception ignored) {
                }
        }
    }
}
