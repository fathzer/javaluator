package examples;

import java.util.Iterator;

import net.astesana.javaluator.*;

/** An example of how to extend an existing evaluator by adding a new supported function.
 */
public class ExtendedDoubleEvaluator {
	/** Defines the new function (square root).*/
	private static final Function SQRT = new Function("sqrt", 1);
	
	public static void main(String[] args) {
		// Create an array of function with all the built-in DoubleEvaluator's functions and the new sqrt function
		Function[] functions = new Function[DoubleEvaluator.FUNCTIONS.length+1];
		functions[0] = SQRT;
		System.arraycopy(DoubleEvaluator.FUNCTIONS, 0, functions, 1, DoubleEvaluator.FUNCTIONS.length);
		
		// Create a new subclass of DoubleEvaluator that support the new function
		AbstractEvaluator<Double> evaluator = new DoubleEvaluator(DoubleEvaluator.OPERATORS, functions, DoubleEvaluator.CONSTANTS) {
			@Override
			protected Double evaluate(Function function, Iterator<Double> arguments) {
				if (function == SQRT) {
					// Implements the new function
					return Math.sqrt(arguments.next());
				} else {
					// If it's another function, pass it to DoubleEvaluator
					return super.evaluate(function, arguments);
				}
			}
		};
		
		// Test that all this stuff is ok
		String expression = "sqrt(abs(-2))^2";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}

}
