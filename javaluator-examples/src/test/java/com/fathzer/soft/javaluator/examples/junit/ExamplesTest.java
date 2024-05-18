package com.fathzer.soft.javaluator.examples.junit;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Parameters;
import com.fathzer.soft.javaluator.examples.BooleanSetEvaluator;
import com.fathzer.soft.javaluator.examples.LocalizedEvaluator;
import com.fathzer.soft.javaluator.examples.SimpleBooleanEvaluator;
import com.fathzer.soft.javaluator.examples.BooleanSetEvaluator.BitSetEvaluationContext;
import com.fathzer.soft.javaluator.examples.ExtendedDoubleEvaluator;
import com.fathzer.soft.javaluator.examples.TextualOperatorsEvaluator;

public class ExamplesTest {
	
	@Test
	public void testBooleanSetEvaluator() {
		BooleanSetEvaluator evaluator = new BooleanSetEvaluator();
		BitSetEvaluationContext context = new BitSetEvaluationContext(4);
		assertEquals("001", BooleanSetEvaluator.toBinaryString(evaluator.evaluate("0011 * 1010", context)));
		assertEquals("11", BooleanSetEvaluator.toBinaryString(evaluator.evaluate("true * 1100", context)));
		assertEquals("1111", BooleanSetEvaluator.toBinaryString(evaluator.evaluate("-false", context)));
	}
	
	@Test
	public void testExtended() {
		ExtendedDoubleEvaluator evaluator = new ExtendedDoubleEvaluator();
		// Test that all this stuff is ok
		String expression = "sqrt(abs(-2))^2";
		assertEquals(2, evaluator.evaluate(expression), 0.00001);;
	}
	
	@Test
	public void testLocalizing() {
		LocalizedEvaluator evaluator = new LocalizedEvaluator();
		String expression = "3 000 +moyenne(3 ; somme(1,5 ; 7 ; -3,5))";
		assertEquals(3004, evaluator.evaluate(expression), 0.0001);
	}
	
	@Test
	public void testRestricting() {
		// Let's create a double evaluator that only support +,-,*,and / operators, with no constants,
		// and no functions. The default parenthesis will be allowed
		// First create empty evaluator parameters
		Parameters params = new Parameters();
		// Add the supported operators to these parameters
		params.add(DoubleEvaluator.PLUS);
		params.add(DoubleEvaluator.MINUS);
		params.add(DoubleEvaluator.MULTIPLY);
		params.add(DoubleEvaluator.DIVIDE);
		params.add(DoubleEvaluator.NEGATE);
		// Add the default expression brackets
		params.addExpressionBracket(BracketPair.PARENTHESES);
		// Create the restricted evaluator
		DoubleEvaluator evaluator = new DoubleEvaluator(params);
		
		// Let's try some expressions
		assertEquals(-10,evaluator.evaluate("(3*-4)+2"),0.00001);
		try {
			// use an unknown operator
			evaluator.evaluate("3^2");
			fail("Should have fail to use ^ operator");
		} catch (IllegalArgumentException e) {
			// The evaluation thrown the excepted exception
		}
		try {
			// use an unknown function
			evaluator.evaluate("ln(5)");
			fail("Should have fail to use ln function");
		} catch (IllegalArgumentException e) {
			// The evaluation thrown the excepted exception
		}
	}
	
	@Test
	public void testTextualOperators() {
		Map<String,String> variableToValue = new HashMap<String, String>();
		variableToValue.put("type", "PORT");
		AbstractEvaluator<Boolean> evaluator = new TextualOperatorsEvaluator();
		assertTrue(evaluator.evaluate("type=PORT", variableToValue));
		assertFalse(evaluator.evaluate("type=NORTH", variableToValue));
		assertTrue(evaluator.evaluate("type=PORT AND true", variableToValue));
	}
	
	@Test
	public void testSimpleBoolean() {
		SimpleBooleanEvaluator evaluator = new SimpleBooleanEvaluator();
		assertFalse(evaluator.evaluate("true && false"));
		assertTrue(evaluator.evaluate("true || false"));
		assertFalse(evaluator.evaluate("!true"));
	}
}
