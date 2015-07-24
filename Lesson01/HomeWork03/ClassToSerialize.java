package Lesson01.HomeWork03;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by st on 22.07.2015.
 */
public class ClassToSerialize implements Serializable {
    @Save
    public String s1 = "String #1";
    private String s2 = "String #2";
    @Save
    public static String s3 = "Static String";
    @Save
    public int i1 = 1;
    @Save
    public int i2 = 2;
    @Save
    public TestClass testClass = new TestClass();

    class TestClass implements Serializable
    {
        public int a = 0;
        public int b = 0;
        public Long aLong = 0L;
    }

}


