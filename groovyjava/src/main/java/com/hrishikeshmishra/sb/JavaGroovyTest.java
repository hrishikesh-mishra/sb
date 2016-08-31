package com.hrishikeshmishra.sb;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class JavaGroovyTest {

    static final GroovyClassLoader classLoader = new GroovyClassLoader();

    public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {
        example6();
    }

    private static void example6() throws IOException, IllegalAccessException, InstantiationException {

        Class groovy = classLoader.parseClass(new File("/Users/hrishikesh.mishra/hrishi/codes/sb/groovyjava/src/main/resources/SampleScript2.groovy"));
        GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        Obj obj = new Obj(new HashMap<String, Integer>(){{put("V", 10);}});
        String output = (String) groovyObj.invokeMethod("process", new Object[] {obj });
        System.out.println(output);
    }
}
