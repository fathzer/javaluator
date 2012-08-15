package net.astesana.javaluator.junit;

import static org.junit.Assert.*;

import net.astesana.javaluator.DoubleEvaluator;

import org.junit.Test;

public class DoubleEvaluatorTest {
	private static DoubleEvaluator evaluator = new DoubleEvaluator();

	@Test
	public void testResults() {
		assertEquals(2, evaluator.evaluate("6/3"),0.001);
		assertEquals(Double.POSITIVE_INFINITY, evaluator.evaluate("2/0"),0.001);
		assertEquals(2, evaluator.evaluate("7 % 2.5"),0.001);
		assertEquals(-1., evaluator.evaluate("-1"), 0.001);
		assertEquals(1., evaluator.evaluate("1"), 0.001);
		assertEquals(-3, evaluator.evaluate("1+-4"), 0.001);
		assertEquals(2, evaluator.evaluate("3-1"), 0.001);
		assertEquals(-4, evaluator.evaluate("-2^2"),0.001);
		
		assertEquals(0, evaluator.evaluate("sin(pi)"),0.001);
		assertEquals(1, evaluator.evaluate("ln(e)"),0.001);
		assertEquals(2, evaluator.evaluate("log(100)"),0.001);
		assertEquals(-1, evaluator.evaluate("min(1,-1)"),0.001);
		assertEquals(-1, evaluator.evaluate("min(8,3,1,-1)"),0.001);
		assertEquals(11, evaluator.evaluate("sum(8,3,1,-1)"),0.001);
		
		assertEquals(-1, evaluator.evaluate("min(1,min(3+2,2))+-(4*0.5)"),0.001);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void test2ValuesFollowing() {
		evaluator.evaluate("10 5 +");
	}

	@Test (expected=IllegalArgumentException.class)
	public void test2ValuesFollowing2() {
		evaluator.evaluate("(10) (5)");
	}

	@Test (expected=IllegalArgumentException.class)
	public void test2OperatorsFollowing() {
		evaluator.evaluate("10**5");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testMissingEndValue() {
		evaluator.evaluate("10*");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testNoFunctionArgument() {
		evaluator.evaluate("sin()");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testEmptyFunctionArgument() {
		evaluator.evaluate("min(,2)");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testSomethingBetweenFunctionAndOpenBracket() {
		evaluator.evaluate("sin3(45)");
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testMissingArgument() {
		evaluator.evaluate("min(1,)");
	}
}
