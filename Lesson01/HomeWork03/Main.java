package HomeWork03;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by st on 22.07.2015.
 */
public class Main {

    private static void customSerialize(Object o, String filename) throws IOException {
        Class ctsclass = o.getClass();
        Field[] fields = ctsclass.getDeclaredFields();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));

        for (Field f : fields) {
            Annotation[] fieldAnnotations = f.getAnnotations();
            for (Annotation a : fieldAnnotations)
                if (a instanceof Save) {
                    try {
                        oos.writeObject(f.getName());
                        oos.writeObject(f.getType());
                        oos.writeObject(f.get(o));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
        }
        oos.flush();
        oos.close();
    }

    private static void customDeserialize(Object o, String filename) throws IOException {
        Class classNewInstance = o.getClass();
        Field[] newInstanceFields = classNewInstance.getFields();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        try {
            while (true) {
                try {
                    String name = (String) ois.readObject();
                    Type t = (Type) ois.readObject();
                    Object value = ois.readObject();

                    for (Field f : newInstanceFields) {
                        if (f.getName().equals(name)) {
                            try {
                                f.set(o, value);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (EOFException e) {
            ois.close();
        }

    }

    public static void main(String[] args) throws IOException {
        final String filename = "c:\\serializedata.ser";

        ClassToSerialize classToSerialize = new ClassToSerialize();
        customSerialize(classToSerialize, filename);

        classToSerialize.i1 = 10;
        classToSerialize.i2 = 20;
        classToSerialize.s1 = "New String";
        classToSerialize.testClass.aLong = 256L;

        ClassToSerialize newInstance = new ClassToSerialize();
        customDeserialize(newInstance, filename);

        System.out.println(classToSerialize.i1 + " : " + newInstance.i1);
        System.out.println(classToSerialize.i2 + " : " + newInstance.i2);
        System.out.println(classToSerialize.s1 + " : " + newInstance.s1);
        System.out.println(classToSerialize.testClass + " : " + newInstance.testClass);

    }
}
