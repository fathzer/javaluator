package examples;

import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.StaticVariableSet;

/** An example of how to use variables in evaluators.
 * <br>This example output the values of <i>sin(x)</i> for many values of x.
 */
public class Variable {
	public static void main(String[] args) {
		// Create the evaluator
		final DoubleEvaluator eval = new DoubleEvaluator();
		final String expression = "sin(x)"; // The expression to evaluate
		// Create a new empty variable set
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		double x = 0;
		final double step = Math.PI/8;
		while (x<=Math.PI/2) {
			// Set the value of x
			variables.set("x", x);
			// Evaluate the expression
			Double result = eval.evaluate(expression, variables);
			System.out.println("x="+x+" -> "+expression+" = "+result);
			x += step;
		}
	}
}
