package net.astesana.javaluator.junit;

import static org.junit.Assert.*;

import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.Parameters;
import net.astesana.javaluator.BracketPair;

import org.junit.Test;

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
		DoubleEvaluator eval = new DoubleEvaluator(params);
		return eval;
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
