package examples;

import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.StaticVariableSet;

/** An example of how to use variables in evaluators.
 */
public class Variable {
	public static void main(String[] args) {
		final DoubleEvaluator eval = new DoubleEvaluator();
		final String expression = "sin(x)";
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		double current = 0;
		final double step = Math.PI/8;
		while (current<=Math.PI/2) {
			variables.set("x", current);
			System.out.println("x="+current+" -> "+expression+" = "+eval.evaluate(expression, variables));
			current += step;
		}
	}
}
