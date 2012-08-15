package net.astesana.javaluator;

import java.util.Iterator;

/** An evaluator that is able to evaluate arithmetic expressions on Double.
 */
public class DoubleEvaluator extends AbstractEvaluator<Double> {
	private static class DOperator extends Operator<Double> {
		private DOperator(String operator, int argumentCount, Operator.Associativity isLeftToRight, int precedence) {
			super(operator, argumentCount, isLeftToRight, precedence);
		}

		@Override
		public Double evaluate(Iterator<Double> operands) {
			if (this==NEGATE) {
				return -operands.next();
			} else if (this==MINUS) {
				return operands.next() - operands.next();
			} else if (this==PLUS) {
				return operands.next() + operands.next();
			} else if (this==MULTIPLY) {
				return operands.next() * operands.next();
			} else if (this==DIVIDE) {
				return operands.next() / operands.next();
			} else if (this==EXPONENT) {
				return Math.pow(operands.next(),operands.next());
			} else if (this==MODULO) {
				return operands.next() % operands.next();
			} else {
				throw new IllegalArgumentException ("Unknown operator");
			}
		}
	}
	
	private static class DFunction extends Function<Double> {
		private DFunction(String name, int argumentCount) {
			super(name, argumentCount);
		}

		private DFunction(String name, int minArgumentCount, int maxArgumentCount) {
			super(name, minArgumentCount, maxArgumentCount);
		}

		@Override
		public Double evaluate(Iterator<Double> arguments) {
			double result;
			if (this==SINE) {
				result = Math.sin(arguments.next());
			} else if (this==COSINE) {
				result = Math.cos(arguments.next());
			} else if (this==MIN) {
				result = arguments.next();
				while (arguments.hasNext()) {
					result = Math.min(result, arguments.next());
				}
			} else if (this==MAX) {
				result = arguments.next();
				while (arguments.hasNext()) {
					result = Math.max(result, arguments.next());
				}
			} else if (this==SUM) {
				result = 0;
				while (arguments.hasNext()) {
					result = result + arguments.next();
				}
			} else if (this==LN) {
				result = Math.log(arguments.next());
			} else if (this==LOG) {
				result = Math.log10(arguments.next());
			} else {
				throw new IllegalArgumentException ("Unknown operator");
			}
			return result;
		}
	}
	
	/** A constant that represents pi (3.14159...) */
	public final static Constant<Double> PI = new Constant<Double>("pi", Math.PI);
	/** A constant that represents e (2.718281...) */
	public final static Constant<Double> E = new Constant<Double>("e", Math.E);
	
	/** Returns the trigonometric sine of an angle. The angle is expressed in radian.*/
	public final static Function<Double> SINE = new DFunction("sin", 1);
	/** Returns the trigonometric cosine of an angle. The angle is expressed in radian.*/
	public final static Function<Double> COSINE = new DFunction("cos", 1);
	/** Returns the minimum of n numbers (n>=1) */
	public final static Function<Double> MIN = new DFunction("min", 1, Integer.MAX_VALUE);
	/** Returns the maximum of n numbers (n>=1) */
	public final static Function<Double> MAX = new DFunction("max", 1, Integer.MAX_VALUE);
	/** Returns the sum of n numbers (n>=1) */
	public final static Function<Double> SUM = new DFunction("sum", 1, Integer.MAX_VALUE);
	/** Returns the natural logarithm of a number */
	public final static Function<Double> LN = new DFunction("ln", 1);
	/** Returns the decimal logarithm of a number */
	public final static Function<Double> LOG = new DFunction("log", 1);

	public final static Operator<Double> NEGATE = new DOperator("-", 1, Operator.Associativity.RIGHT, 3);
	public final static Operator<Double> MINUS = new DOperator("-", 2, Operator.Associativity.LEFT, 1);
	public final static Operator<Double> PLUS = new DOperator("+", 2, Operator.Associativity.LEFT, 1);
	public final static Operator<Double> MULTIPLY = new DOperator("*", 2, Operator.Associativity.LEFT, 2);
	public final static Operator<Double> DIVIDE = new DOperator("/", 2, Operator.Associativity.LEFT, 2);
	public final static Operator<Double> EXPONENT = new DOperator("^", 2, Operator.Associativity.LEFT, 4);
	/** The <a href="http://en.wikipedia.org/wiki/Modulo_operation">modulo operator</a>.*/
	public final static Operator<Double> MODULO = new DOperator("%", 2, Operator.Associativity.LEFT, 4);

	/** The whole set of predefined operators */
	public static final Operator<Double>[] OPERATORS = new Operator[]{NEGATE, MINUS, PLUS, MULTIPLY, DIVIDE, EXPONENT, MODULO};
	/** The whole set of predefined functions */
	public static final Function<Double>[] FUNCTIONS = new Function[]{SINE, COSINE, MIN, MAX, SUM, LN, LOG};
	/** The whole set of predefined constants */
	public static final Constant<Double>[] CONSTANTS = new Constant[]{PI, E};
	
	public DoubleEvaluator() {
		this(OPERATORS, FUNCTIONS, CONSTANTS);
	}

	public DoubleEvaluator(Operator<Double>[] operators, Function<Double>[] functions, Constant<Double>[] constants) {
		super(operators, functions, constants);
	}

	@Override
	protected Double toValue(String literal) {
		try {
			return Double.parseDouble(literal);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(literal+" is not a number");
		}
	}
}
