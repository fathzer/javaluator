package examples;

import java.util.Iterator;

import net.astesana.javaluator.*;
import net.astesana.javaluator.Operator.Associativity;

/** An example of how to extend an existing evaluator by adding support for a new function and a new operator.
 */
public class Extending {
	/** Defines the new function (square root).*/
	private static final Function SQRT = new Function("sqrt", 1);
	/** Defines the new operator (factorial).*/
	private static final Operator FACT = new Operator("!", 1, Associativity.LEFT, DoubleEvaluator.NEGATE_HIGH.getPrecedence()+1);
	
	public static void main(String[] args) {
		// Gets the default DoubleEvaluator's parameters
		Parameters params = DoubleEvaluator.getDefaultParameters();
		// add the new sqrt function to these parameters
		params.add(SQRT);
		// add the new operator to these parameters
		params.add(FACT);
		
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

			@Override
			protected Double evaluate(Operator operator, Iterator<Double> operands, Object evaluationContext) {
				if (operator.equals(FACT)) {
					return fact(operands);
				} else {
					return super.evaluate(operator, operands, evaluationContext);
				}
			}

			private Double fact(Iterator<Double> operands) {
				Double result = operands.next();
				if (result<0) throw new IllegalArgumentException("Can't compute factorial of a negative number");
				if ((result != Math.floor(result)) || Double.isInfinite(result)) throw new IllegalArgumentException("Can't compute factorial of a non integer number");
				if (operands.hasNext()) throw new IllegalArgumentException("wrong operand count in factorial operator");
				double it = result;
				while (it>1) {
					it = it - 1;
					result = result*it;
				}
				return result;
			}
		};
		
		// Test that all this stuff is ok
		String expression = "4!-2";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}

}
