package examples;

import net.astesana.javaluator.DoubleEvaluator;

/** The most simple class to use the built-in real expression evaluator.*/
public class Simplest {
	public static void main(String[] args) {
		// Create a new evaluator
		DoubleEvaluator evaluator = new DoubleEvaluator();
		String expression = "(2^3-1)*sin(pi/4)/ln(pi^2)";
		// Evaluate an expression
		Double result = evaluator.evaluate(expression);
		// Ouput the result
		System.out.println(expression + " = " + result);
	}
}