package com.hrishikeshmishra.sb.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;


public class SplApplication {

	public static void main(String[] args) {
		example12();
		example11();
		example10();
		example9();
		example8();
		example7();
		example6();
		example5();
		example4();
		example3();
		example2();
		example1();
	}

	private static void example12(){
		// create an array of integers
		List<Integer> primes = new ArrayList<Integer>();
		primes.addAll(Arrays.asList(2,3,5,7,11,13,17));

		// create parser and set variable 'primes' as the array of integers
		ExpressionParser parser = new SpelExpressionParser();
		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariable("primes", primes);

		// all prime numbers > 10 from the list (using selection ?{...})
		// evaluates to [11, 13, 17]
		List<Integer> primesGreaterThanTen = (List<Integer>) parser.parseExpression(
				"#primes.?[#this>10]").getValue(context);

		System.out.println(primesGreaterThanTen);
	}

	private static void example11(){
		Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
		StandardEvaluationContext context = new StandardEvaluationContext(tesla);
		context.setVariable("newName", "Mike Tesla");
		ExpressionParser parser = new SpelExpressionParser();
		parser.parseExpression("name = #newName").getValue(context);
		System.out.println(tesla.getName()) ;
	}

	private static void example10(){
		ExpressionParser parser = new SpelExpressionParser();

// evals to "Hello World"
		String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();
		double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();
		int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
		boolean trueValue = (Boolean) parser.parseExpression("true").getValue();
		Object nullValue = parser.parseExpression("null").getValue();
		System.out.println(helloWorld +", " + avogadrosNumber+ ", " + maxValue +", " + trueValue +
		", " + nullValue);

	}

	private static void example9(){
		// Turn on:
// - auto null reference initialization
// - auto collection growing
		SpelParserConfiguration config = new SpelParserConfiguration(true,true);

		ExpressionParser parser = new SpelExpressionParser(config);

		Expression expression = parser.parseExpression("list[30]");

		Demo demo = new Demo();

		Object o = expression.getValue(demo);

// demo.list will now be a real collection of 4 entries
// Each entry is a new empty String

		System.out.println(demo.list);
	}


	private static void example8(){

		Simple simple = new Simple();

		simple.booleanList.add(true);

		StandardEvaluationContext simpleContext = new StandardEvaluationContext(simple);

		ExpressionParser parser = new SpelExpressionParser();
		// false is passed in here as a string. SpEL and the conversion service will
		// correctly recognize that it needs to be a Boolean and convert it
		parser.parseExpression("booleanList[0]").setValue(simpleContext, "false");

		// b will be false
		Boolean b = simple.booleanList.get(0);
		System.out.println(b);
	}

	private static void example7(){
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

// The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name == 'Nikola Tesla1'?false:true");
		EvaluationContext context = new StandardEvaluationContext(tesla);
		boolean result = exp.getValue(context, Boolean.class);
		System.out.println(result);
	}

	private static void example6(){
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);

// The constructor arguments are name, birthday, and nationality.
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name");
		String name = (String) exp.getValue(tesla);
		System.out.println(name);
	}

	private static void  example5(){
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);
		Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name");

		EvaluationContext context = new StandardEvaluationContext(tesla);
		String name = (String) exp.getValue(context);
		System.out.println(name);
	}

	private static void example4(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("new String('hello world').toUpperCase()");
		String message = exp.getValue(String.class);
		System.out.println(message);
	}
	private static void example1(){
		ExpressionParser parser = new SpelExpressionParser();

		Expression exp = parser.parseExpression("'Hello World'");
		String message = (String) exp.getValue();
		System.out.println(message);
	}

	private static void example2(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello World'.concat('!')");
		String message = (String) exp.getValue();
		System.out.println(message);
	}


	private static void example3(){
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello World'.bytes.length");
		int length = (Integer) exp.getValue();
		System.out.println(length);
	}


}
