package com.fathzer.soft.javaluator.junit.examples;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fathzer.soft.javaluator.examples.BooleanSetEvaluator;
import com.fathzer.soft.javaluator.examples.SimpleBooleanEvaluator;
import com.fathzer.soft.javaluator.examples.BooleanSetEvaluator.BitSetEvaluationContext;
import com.fathzer.soft.javaluator.examples.ExtendedDoubleEvaluator;

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
		fail("Not implemented");
	}
	
	@Test
	public void testRestricting() {
		fail("Not implemented");
	}
	
	@Test
	public void testTextualOperators() {
		fail("Not implemented");
	}
	
	@Test
	public void testSimpleBoolean() {
		SimpleBooleanEvaluator evaluator = new SimpleBooleanEvaluator();
		assertFalse(evaluator.evaluate("true && false"));
		assertTrue(evaluator.evaluate("true || false"));
		assertFalse(evaluator.evaluate("!true"));
	}

}
