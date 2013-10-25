package com.fathzer.soft.javaluator.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Parameters;

public class ParametersTest {
	
	@Test
	public void testMultipleTranslatedName() {
		Parameters params = DoubleEvaluator.getDefaultParameters();
		params.setTranslation(DoubleEvaluator.AVERAGE, "moyenne");
		params.setTranslation(DoubleEvaluator.AVERAGE, "moy");
		DoubleEvaluator evaluator = new DoubleEvaluator(params);
		assertEquals(2, evaluator.evaluate("moy(3,1)"),0.001);
	}

	@Test
	public void testFunctionArgumentSeparators() {
		Parameters params = DoubleEvaluator.getDefaultParameters();
		params.setFunctionArgumentSeparator(';');
		DoubleEvaluator evaluator = new DoubleEvaluator(params);
		assertEquals(2, evaluator.evaluate("avg(3;1)"),0.001);
	}

//	@Test (expected=IllegalArgumentException.class)
//	public void sameTranslatedNames() {
//		Parameters params = new Parameters();
//		params.setTranslation(DoubleEvaluator.AVERAGE, "moy");
//		params.setTranslation(DoubleEvaluator.SUM, "moy");
//		DoubleEvaluator eval = new DoubleEvaluator(params);
//	}
}
