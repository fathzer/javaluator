package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

public class UnaryOperatorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testTrailingOperator() {
		new SimpleBooleanEvaluator().evaluate("true!");
	}

	@Test
	public void testPrefixOperator() {
		SimpleBooleanEvaluator evaluator = new SimpleBooleanEvaluator();
		assertFalse(evaluator.evaluate("!true"));
		assertTrue(evaluator.evaluate("!!true"));
	}

	@Test
	public void testPostfixOperator() {
		// A left associative unary operator is a postfix operator
		Parameters params = new Parameters();
		params.add(new Operator("!", 1, Operator.Associativity.LEFT, 3));
		params.add(DoubleEvaluator.NEGATE);
		params.add(DoubleEvaluator.MINUS);
		AbstractEvaluator<Double> evaluator = new AbstractEvaluator<Double>(params) {
			@Override
			protected Double toValue(String literal, Object evaluationContext) {
				return Double.valueOf(literal);
			}
			@Override
			protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
				if (operator.getOperandCount()==1) {
					return -operands.next();
				} else {
					return operands.next() - operands.next();
				}
			}
		};
		assertEquals(-4., evaluator.evaluate("4!"), 0.001);
		assertEquals(4., evaluator.evaluate("4!!"), 0.001);
		// After a postfix operator, the '-' homonym is the binary one
		assertEquals(-5., evaluator.evaluate("4!-1"), 0.001);
		assertEquals(-4., evaluator.evaluate("-4"), 0.001);
		try {
			evaluator.evaluate("!4");
			fail("A postfix operator can't appear before its operand");
		} catch (IllegalArgumentException e) {
			// The expected exception was thrown
		}
	}
}
