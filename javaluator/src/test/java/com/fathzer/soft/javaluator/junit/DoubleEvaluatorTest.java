package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import com.fathzer.soft.javaluator.DoubleEvaluator.Style;

public class DoubleEvaluatorTest {
	private static final DoubleEvaluator EVALUATOR = new DoubleEvaluator();
	
	@Test
	public void excelLike() {
		DoubleEvaluator excelLike = new DoubleEvaluator(DoubleEvaluator.getDefaultParameters(Style.EXCEL));
		assertEquals(4, excelLike.evaluate("-2^2"), 0.001);
	}

	@Test
	public void testResults() {
		assertEquals(-2, EVALUATOR.evaluate("2+-2^2"),0.001);
		assertEquals(2, EVALUATOR.evaluate("6 / 3"),0.001);
		assertEquals(Double.POSITIVE_INFINITY, EVALUATOR.evaluate("2/0"),0.001);
		assertEquals(2, EVALUATOR.evaluate("7 % 2.5"),0.001);
		assertEquals(-1., EVALUATOR.evaluate("-1"), 0.001);
		assertEquals(1., EVALUATOR.evaluate("1"), 0.001);
		assertEquals(-3, EVALUATOR.evaluate("1+-4"), 0.001);
		assertEquals(2, EVALUATOR.evaluate("3-1"), 0.001);
		assertEquals(-4, EVALUATOR.evaluate("-2^2"),0.001);
		assertEquals(2, EVALUATOR.evaluate("4^0.5"),0.001);
		assertEquals(262144, EVALUATOR.evaluate("4^3^2"), 0.001);

		assertEquals(1, EVALUATOR.evaluate("sin ( pi /2)"),0.001);
		assertEquals(-1, EVALUATOR.evaluate("cos(pi)"),0.001);
		assertEquals(1, EVALUATOR.evaluate("tan(pi/4)"),0.001);
		assertEquals(Math.PI, EVALUATOR.evaluate("acos( -1)"),0.001);
		assertEquals(Math.PI/2, EVALUATOR.evaluate("asin(1)"),0.001);
		assertEquals(Math.PI/4, EVALUATOR.evaluate("atan(1)"),0.001);
		
		assertEquals(1, EVALUATOR.evaluate("ln(e)"),0.001);
		assertEquals(2, EVALUATOR.evaluate("log(100)"),0.001);
		assertEquals(-1, EVALUATOR.evaluate("min(1,-1)"),0.001);
		assertEquals(-1, EVALUATOR.evaluate("min(8,3,1,-1)"),0.001);
		assertEquals(11, EVALUATOR.evaluate("sum(8,3,1,-1)"),0.001);
		assertEquals(3, EVALUATOR.evaluate("avg(8,3,1,0)"),0.001);
		
		assertEquals(3, EVALUATOR.evaluate("abs(-3)"),0.001);
		assertEquals(3, EVALUATOR.evaluate("ceil(2.45)"),0.001);
		assertEquals(2, EVALUATOR.evaluate("floor(2.45)"),0.001);
		assertEquals(2, EVALUATOR.evaluate("round(2.45)"),0.001);
		
		double rnd = EVALUATOR.evaluate("random()");
		assertTrue(rnd>=0 && rnd<=1.0);

		assertEquals(EVALUATOR.evaluate("tanh(5)"), EVALUATOR.evaluate("sinh(5)/cosh(5)"),0.001);
		
		assertEquals(-1, EVALUATOR.evaluate("min(1,min(3+2,2))+-round(4.1)*0.5"),0.001);
	}
	
	@Test
	public void testWithVariable() {
		String expression = "x+2";
		StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		
		variables.set("x", 1.);
		assertEquals(3, EVALUATOR.evaluate(expression, variables),0.001);
		variables.set("x", -1.);
		assertEquals(1, EVALUATOR.evaluate(expression, variables),0.001);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void test2ValuesFollowing() {
		EVALUATOR.evaluate("10 5 +");
	}

	@Test (expected=IllegalArgumentException.class)
	public void test2ValuesFollowing2() {
		EVALUATOR.evaluate("(10) (5)");
	}

	@Test (expected=IllegalArgumentException.class)
	public void test2OperatorsFollowing() {
		EVALUATOR.evaluate("10**5");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testMissingEndValue() {
		EVALUATOR.evaluate("10*");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNoFunctionArgument() {
		EVALUATOR.evaluate("sin()");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testNoFunctionArgument2() {
		EVALUATOR.evaluate("average()");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testEmptyFunctionArgument() {
		EVALUATOR.evaluate("min(,2)");
	}

	@Test (expected=IllegalArgumentException.class)
	public void TestFunctionSeparatorHidenByBrackets() {
		new DoubleEvaluator().evaluate("min((10,15),20)");
	}

	@Test
	public void TestComplexFunctionAndParenthesis() {
		assertEquals(15.0,new DoubleEvaluator().evaluate("min((max(((10-6)*2),7,(15))),19+min(1,5))"), 0.001);
	}
	
	@Test
	public void TestScientificNotation() {
		DoubleEvaluator evaluator = new DoubleEvaluator(DoubleEvaluator.getDefaultParameters(), true);
		assertEquals(3e-2, evaluator.evaluate("3e-2"), 1e-5);
		assertEquals(30.9317695E+115, evaluator.evaluate("30.9317695E+115"), 1.0);
	}

	@Test (expected=IllegalArgumentException.class)
	public void testSomethingBetweenFunctionAndOpenBracket() {
		EVALUATOR.evaluate("sin3(45)");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testMissingArgument() {
		EVALUATOR.evaluate("min(1,)");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidArgumentACos() {
		EVALUATOR.evaluate("acos(2)");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidArgumentASin() {
		EVALUATOR.evaluate("asin(2)");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testOnlyCloseBracket() {
		EVALUATOR.evaluate(")");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testDSuffixInLiteral() {
		EVALUATOR.evaluate("3d+4");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testStartWithFunctionSeparator() {
		EVALUATOR.evaluate(",3");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testNoArgInAverageFunction() {
		EVALUATOR.evaluate("avg()");
	}
}
