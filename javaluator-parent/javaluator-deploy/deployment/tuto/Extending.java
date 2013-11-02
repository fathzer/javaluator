package com.fathzer.soft.javaluator.examples;

import java.util.Iterator;

import com.fathzer.soft.javaluator.*;

/** An example of how to extend an existing evaluator by adding support for a new function.
 */
public class Extending {
	/** Defines the new function (square root).*/
	private static final Function SQRT = new Function("sqrt", 1);
	
	public static void main(String[] args) {
		// Gets the default DoubleEvaluator's parameters
		Parameters params = DoubleEvaluator.getDefaultParameters();
		// add the new sqrt function to these parameters
		params.add(SQRT);
		
		// Create a new subclass of DoubleEvaluator that support the new function
		AbstractEvaluator<Double> evaluator = new DoubleEvaluator(params) {
			@Override
			protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
				if (function == SQRT) {
					// Implements the new function
					return Math.sqrt(arguments.next());
				} else {
					// If it's another function, pass it to DoubleEvaluator
					return super.evaluate(function, arguments, evaluationContext);
				}
			}
		};
		
		// Test that all this stuff is ok
		String expression = "sqrt(abs(-2))^2";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}

}
