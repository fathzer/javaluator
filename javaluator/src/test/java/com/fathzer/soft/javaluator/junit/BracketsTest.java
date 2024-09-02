package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Parameters;

public class BracketsTest {

	@Test
	public void test() {
		DoubleEvaluator eval = buildEvaluator();
		assertEquals(1.0, eval.evaluate("[(0.5)+(0.5)]"), 0.0001);
		assertEquals(1.0, eval.evaluate("sin<[pi/2]>"), 0.0001);
	}

	private DoubleEvaluator buildEvaluator() {
		Parameters defaultParams = DoubleEvaluator.getDefaultParameters();
		Parameters params = new Parameters();
		params.add(DoubleEvaluator.SINE);
		params.addConstants(defaultParams.getConstants());
		params.addOperators(defaultParams.getOperators());
		params.addExpressionBracket(BracketPair.PARENTHESES);
		params.addExpressionBracket(BracketPair.BRACKETS);
		params.addFunctionBracket(BracketPair.ANGLES);
		return new DoubleEvaluator(params);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void testInvalidBrackets() {
		new DoubleEvaluator().evaluate("[(0.5)+(0.5)]");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testInvalidBracketsMatching() {
		buildEvaluator().evaluate("([0.5)+(0.5)]");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testInvalidFunctionBrackets() {
		buildEvaluator().evaluate("sin[0.5]");
	}

	@Test (expected=IllegalArgumentException.class)
	public void testInvalidExpressionBrackets() {
		buildEvaluator().evaluate("<0.5*2>");
	}
}
