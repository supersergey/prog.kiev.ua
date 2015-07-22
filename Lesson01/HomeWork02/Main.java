package HomeWork02;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by user on 22.07.2015.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        TextContainer txContainer = new TextContainer();
        Class tcclx = txContainer.getClass();

        String filename = "";

        for (Annotation ca : tcclx.getAnnotations())
            if (ca instanceof SaveTo)
                filename = ((SaveTo) ca).filename();

        for (Method m : tcclx.getDeclaredMethods()) {
            for (Annotation ma : m.getAnnotations())
                try {
                    if (ma instanceof Saver) {
                        m.invoke(txContainer, filename);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
