package com.hrishikeshmishra.sb;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;

import java.io.File;
import java.io.IOException;

public class GroovyJavaApplication {
    static final GroovyShell shell = new GroovyShell();
    static final GroovyClassLoader classLoader = new GroovyClassLoader();

	public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {
        example6();
	    example4();
	    example3();
	    example2();
        example1();
	}

	private static void example6() throws IOException, IllegalAccessException, InstantiationException {
        // Load Groovy script file.

        Class groovy = classLoader.parseClass(new File("/Users/hrishikesh.mishra/hrishi/codes/sb/groovyjava/src/main/resources/SampleScript2.groovy"));

        GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        String output = (String) groovyObj.invokeMethod("scriptSays", new Object[] { new Obj(12) });
        System.out.println(output);
    }

	private static void example4() throws IOException, IllegalAccessException, InstantiationException {
        // Create GroovyClassLoader.


        // Create a String with Groovy code.
        final StringBuilder groovyScript = new StringBuilder();
        groovyScript.append("class Sample {");
        groovyScript.append("  String sayIt(name) { \"Groovy says: Cool $name!\" }");
        groovyScript.append("}");

        // Load string as Groovy script class.
        Class groovy = classLoader.parseClass(groovyScript.toString());
        GroovyObject groovyObj = (GroovyObject) groovy.newInstance();
        String output = (String) groovyObj.invokeMethod("sayIt", new Object[] { "mrhaki" });
        System.out.println(output);

        // Load Groovy script file.
        groovy = classLoader.parseClass(new File("/Users/hrishikesh.mishra/hrishi/codes/sb/groovyjava/src/main/resources/SampleScript.groovy"));
        groovyObj = (GroovyObject) groovy.newInstance();
        output = (String) groovyObj.invokeMethod("scriptSays", new Object[] { "mrhaki", new Integer(2) });
        System.out.println(output);

    }

	private static void example3(){
        Binding binding = new Binding();
        binding.setVariable("input", 2);
        GroovyShell shell = new GroovyShell(binding);


        String script = "return input*6";
        Object result = shell.evaluate(script);
        System.out.println(result);


    }
	private static void example2(){
        String command = "assert Eval.me('33*3') == 99";
        shell.evaluate(command);
    }

	private static void example1(){
		String processingCode = "def hello_world() { println 'Hello, world!' }; hello_world();";
		shell.evaluate(processingCode);
	}
}


