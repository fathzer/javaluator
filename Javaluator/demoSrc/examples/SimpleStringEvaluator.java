package examples;

import java.util.Iterator;

import net.astesana.javaluator.*;

public class SimpleStringEvaluator extends AbstractEvaluator<String> {
	private static final Function TO_UPPER = new Function("toUpper", 1);
	private static final Operator CONCAT = new Operator("+", 2, Operator.Associativity.LEFT, 1);
	
	protected SimpleStringEvaluator() {
		super(new Operator[]{CONCAT}, new Function[]{TO_UPPER}, null, false);
	}

	@Override
	protected String toValue(String literal) {
		return literal;
	}

	@Override
	protected String evaluate(Operator operator, Iterator<String> operands) {
		if (operator == CONCAT) {
			return operands.next()+operands.next();
		} else {
			throw new RuntimeException ("Unexpected operator: "+operator.getSymbol());
		}
	}

	@Override
	protected String evaluate(Function function, Iterator<String> arguments) {
		if (function == TO_UPPER) {
			return arguments.next().toUpperCase();
		} else {
			throw new RuntimeException ("Unexpected function: "+function.getName());
		}
	}

	public static void main(String[] args) {
		SimpleStringEvaluator evaluator = new SimpleStringEvaluator();
		String expression = "Hello +toUpper(World)";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}
}
