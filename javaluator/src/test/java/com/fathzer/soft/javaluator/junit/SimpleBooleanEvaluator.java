package com.fathzer.soft.javaluator.junit;

import java.util.Iterator;

import com.fathzer.soft.javaluator.*;

/** An example of how to implement an evaluator from scratch.
 */
public class SimpleBooleanEvaluator extends AbstractEvaluator<Boolean> {
	/** The negate unary operator.*/
	public static final Operator NEGATE = new Operator("!", 1, Operator.Associativity.RIGHT, 3);
	/** The logical AND operator.*/
	private static final Operator AND = new Operator("&&", 2, Operator.Associativity.LEFT, 2);
	/** The logical OR operator.*/
	public static final Operator OR = new Operator("||", 2, Operator.Associativity.LEFT, 1);

	private static final Parameters PARAMETERS;
	
	static {
		// Create the evaluator's parameters
		PARAMETERS = new Parameters();
		// Add the supported operators
		PARAMETERS.add(AND);
		PARAMETERS.add(OR);
		PARAMETERS.add(NEGATE);
	}

	public SimpleBooleanEvaluator() {
		super(PARAMETERS);
	}

	@Override
	protected Boolean toValue(String literal, Object evaluationContext) {
		return Boolean.valueOf(literal);
	}

	@Override
	protected Boolean evaluate(Operator operator, Iterator<Boolean> operands, Object evaluationContext) {
		if (operator == NEGATE) {
			return !operands.next();
		} else if (operator == OR) {
			Boolean o1 = operands.next();
			Boolean o2 = operands.next();
			return o1 || o2;
		} else if (operator == AND) {
			Boolean o1 = operands.next();
			Boolean o2 = operands.next();
			return o1 && o2;
		} else {
			return super.evaluate(operator, operands, evaluationContext);
		}
	}
}
