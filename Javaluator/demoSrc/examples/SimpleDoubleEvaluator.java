package examples;

import net.astesana.javaluator.*;

/** An example of how to restrict operators, functions and constant of an existing evaluator.
 */
public class SimpleDoubleEvaluator {
	public static void main(String[] args) {
		// Let's create an double evaluator that only support +,-,*,and / operators
		// First create empty evaluator parameters
		Parameters params = new Parameters();
		// Add the supported operators to these parameters
		params.add(DoubleEvaluator.PLUS);
		params.add(DoubleEvaluator.MINUS);
		params.add(DoubleEvaluator.MULTIPLY);
		params.add(DoubleEvaluator.DIVIDE);
		params.add(DoubleEvaluator.NEGATE);
		// Create the restricted evaluator
		DoubleEvaluator evaluator = new DoubleEvaluator(params);
		
		String expression = "3*-4+2";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}
}
