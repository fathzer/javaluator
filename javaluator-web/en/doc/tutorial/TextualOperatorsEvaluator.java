package com.fathzer.soft.javaluator.examples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;

public class TextualOperatorsEvaluator extends AbstractEvaluator<Boolean> {
	/** The negate unary operator. */
	public final static Operator NEGATE = new Operator("NOT", 1, Operator.Associativity.RIGHT, 3);
	/** The logical AND operator. */
	private static final Operator AND = new Operator("AND", 2, Operator.Associativity.LEFT, 2);
	/** The logical OR operator. */
	public final static Operator OR = new Operator("OR", 2, Operator.Associativity.LEFT, 1);
	
	private static final Parameters PARAMETERS;

	static {
		// Create the evaluator's parameters
		PARAMETERS = new Parameters();
		// Add the supported operators
		PARAMETERS.add(AND);
		PARAMETERS.add(OR);
		PARAMETERS.add(NEGATE);
	}

	public TextualOperatorsEvaluator() {
		super(PARAMETERS);
	}

	@Override
	protected Boolean toValue(String literal, Object evaluationContext) {
		int index = literal.indexOf('=');
		if (index>=0) {
			String variable = literal.substring(0, index);
			String value = literal.substring(index+1);
			return value.equals(((Map<String, String>)evaluationContext).get(variable));
		} else {
			return Boolean.valueOf(literal);
		}
	}

	@Override
	protected Boolean evaluate(Operator operator,
			Iterator<Boolean> operands, Object evaluationContext) {
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

	@Override
	protected Iterator<String> tokenize(String expression) {
		return Arrays.asList(expression.split("\\s")).iterator();
	}
	
	public static void main(String[] args) {
		Map<String,String> variableToValue = new HashMap<String, String>();
		variableToValue.put("type", "PORT");
		AbstractEvaluator<Boolean> evaluator = new TextualOperatorsEvaluator();
		System.out.println ("type=PORT -> "+evaluator.evaluate("type=PORT", variableToValue));
		System.out.println ("type=NORTH -> "+evaluator.evaluate("type=NORTH", variableToValue));
		System.out.println ("type=PORT AND true -> "+evaluator.evaluate("type=PORT AND true", variableToValue));
	}
}
