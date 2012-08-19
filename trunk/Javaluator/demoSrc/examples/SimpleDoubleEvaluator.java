package examples;

import net.astesana.javaluator.DoubleEvaluator;
import net.astesana.javaluator.Operator;

public class SimpleDoubleEvaluator {
	public static void main(String[] args) {
		DoubleEvaluator evaluator = new DoubleEvaluator(new Operator[]{
				DoubleEvaluator.PLUS, DoubleEvaluator.MINUS, DoubleEvaluator.MULTIPLY, DoubleEvaluator.DIVIDE, DoubleEvaluator.NEGATE},
				null, null);
		String expression = "3*-4+2";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}
}
