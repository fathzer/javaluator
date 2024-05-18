package com.fathzer.soft.javaluator.junit;

import org.junit.Ignore;
import org.junit.Test;

public class SimpleBooleanEvaluatorTest {

	@Test(expected = IllegalArgumentException.class)
	@Ignore("Should be fixed") //TODO
	public void testTrailingOperator() {
		new SimpleBooleanEvaluator().evaluate("true!");
	}
}
