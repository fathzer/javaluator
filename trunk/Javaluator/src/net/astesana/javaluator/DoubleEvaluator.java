package net.astesana.javaluator;

import java.util.Iterator;

/** An evaluator that is able to evaluate arithmetic expressions on Double.
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public class DoubleEvaluator extends AbstractEvaluator<Double> {
	/** A constant that represents pi (3.14159...) */
	public final static Constant PI = new Constant("pi");
	/** A constant that represents e (2.718281...) */
	public final static Constant E = new Constant("e");
	
	/** Returns the trigonometric sine of an angle. The angle is expressed in radian.*/
	public final static Function SINE = new Function("sin", 1);
	/** Returns the trigonometric cosine of an angle. The angle is expressed in radian.*/
	public final static Function COSINE = new Function("cos", 1);
	/** Returns the trigonometric tangent of an angle. The angle is expressed in radian.*/
	public final static Function TANGENT = new Function("tan", 1);
	/** Returns the trigonometric arc-cosine of an angle. The angle is expressed in radian.*/
	public final static Function ACOSINE = new Function("acos", 1);
	/** Returns the trigonometric arc-sine of an angle. The angle is expressed in radian.*/
	public final static Function ASINE = new Function("asin", 1);
	/** Returns the trigonometric arc-tangent of an angle. The angle is expressed in radian.*/
	public final static Function ATAN = new Function("atan", 1);
	/** Returns the minimum of n numbers (n>=1) */
	public final static Function MIN = new Function("min", 1, Integer.MAX_VALUE);
	/** Returns the maximum of n numbers (n>=1) */
	public final static Function MAX = new Function("max", 1, Integer.MAX_VALUE);
	/** Returns the sum of n numbers (n>=1) */
	public final static Function SUM = new Function("sum", 1, Integer.MAX_VALUE);
	/** Returns the average of n numbers (n>=1) */
	public final static Function AVERAGE = new Function("avg", 1, Integer.MAX_VALUE);
	/** Returns the natural logarithm of a number */
	public final static Function LN = new Function("ln", 1);
	/** Returns the decimal logarithm of a number */
	public final static Function LOG = new Function("log", 1);
	/** Returns the smallest integer >= argument */
	public final static Function CEIL = new Function("ceil", 1);
	/** Returns the largest integer <= argument */
	public final static Function FLOOR = new Function("floor", 1);
	/** Returns the closest integer of a number */
	public final static Function ROUND = new Function("round", 1);
	/** Returns the absolute value of a number */
	public final static Function ABS = new Function("abs", 1);


	/** The negate unary operator.*/
	public final static Operator NEGATE = new Operator("-", 1, Operator.Associativity.RIGHT, 3);
	/** The substraction operator.*/
	public final static Operator MINUS = new Operator("-", 2, Operator.Associativity.LEFT, 1);
	/** The addition operator.*/
	public final static Operator PLUS = new Operator("+", 2, Operator.Associativity.LEFT, 1);
	/** The multiplication operator.*/
	public final static Operator MULTIPLY = new Operator("*", 2, Operator.Associativity.LEFT, 2);
	/** The division operator.*/
	public final static Operator DIVIDE = new Operator("/", 2, Operator.Associativity.LEFT, 2);
	/** The exponentiation operator.*/
	public final static Operator EXPONENT = new Operator("^", 2, Operator.Associativity.LEFT, 4);
	/** The <a href="http://en.wikipedia.org/wiki/Modulo_operation">modulo operator</a>.*/
	public final static Operator MODULO = new Operator("%", 2, Operator.Associativity.LEFT, 2);

	/** The whole set of predefined operators */
	public static final Operator[] OPERATORS = new Operator[]{NEGATE, MINUS, PLUS, MULTIPLY, DIVIDE, EXPONENT, MODULO};
	/** The whole set of predefined functions */
	public static final Function[] FUNCTIONS = new Function[]{SINE, COSINE, TANGENT, ASINE, ACOSINE, ATAN, MIN, MAX, SUM, AVERAGE, LN, LOG, ROUND, CEIL, FLOOR, ABS};
	/** The whole set of predefined constants */
	public static final Constant[] CONSTANTS = new Constant[]{PI, E};
	
	public DoubleEvaluator() {
		this(OPERATORS, FUNCTIONS, CONSTANTS);
	}

	public DoubleEvaluator(Operator[] operators, Function[] functions, Constant[] constants) {
		super(operators, functions, constants, true);
	}

	@Override
	protected Double toValue(String literal) {
		try {
			return Double.parseDouble(literal);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(literal+" is not a number");
		}
	}
	
	/* (non-Javadoc)
	 * @see net.astesana.javaluator.AbstractEvaluator#evaluate(net.astesana.javaluator.Constant)
	 */
	@Override
	protected Double evaluate(Constant constant) {
		if (constant==PI) {
			return Math.PI;
		} else if (constant==E) {
			return Math.E;
		} else {
			return super.evaluate(constant);
		}
	}

	/* (non-Javadoc)
	 * @see net.astesana.javaluator.AbstractEvaluator#evaluate(net.astesana.javaluator.Operator, java.util.Iterator)
	 */
	@Override
	protected Double evaluate(Operator operator, Iterator<Double> operands) {
		if (operator==NEGATE) {
			return -operands.next();
		} else if (operator==MINUS) {
			return operands.next() - operands.next();
		} else if (operator==PLUS) {
			return operands.next() + operands.next();
		} else if (operator==MULTIPLY) {
			return operands.next() * operands.next();
		} else if (operator==DIVIDE) {
			return operands.next() / operands.next();
		} else if (operator==EXPONENT) {
			return Math.pow(operands.next(),operands.next());
		} else if (operator==MODULO) {
			return operands.next() % operands.next();
		} else {
			return super.evaluate(operator, operands);
		}
	}

	/* (non-Javadoc)
	 * @see net.astesana.javaluator.AbstractEvaluator#evaluate(net.astesana.javaluator.Function, java.util.Iterator)
	 */
	@Override
	protected Double evaluate(Function function, Iterator<Double> arguments) {
		Double result;
		if (function==ABS) {
			result = Math.abs(arguments.next());
		} else if (function==CEIL) {
			result = Math.ceil(arguments.next());
		} else if (function==FLOOR) {
			result = Math.floor(arguments.next());
		} else if (function==ROUND) {
			Double arg = arguments.next();
			if (arg==Double.NEGATIVE_INFINITY || arg==Double.POSITIVE_INFINITY) {
				result = arg;
			} else {
				result = (double) Math.round(arg);
			}
		} else if (function==SINE) {
			result = Math.sin(arguments.next());
		} else if (function==COSINE) {
			result = Math.cos(arguments.next());
		} else if (function==TANGENT) {
			result = Math.tan(arguments.next());
		} else if (function==ACOSINE) {
			result = Math.acos(arguments.next());
		} else if (function==ASINE) {
			result = Math.asin(arguments.next());
		} else if (function==ATAN) {
			result = Math.atan(arguments.next());
		} else if (function==MIN) {
			result = arguments.next();
			while (arguments.hasNext()) {
				result = Math.min(result, arguments.next());
			}
		} else if (function==MAX) {
			result = arguments.next();
			while (arguments.hasNext()) {
				result = Math.max(result, arguments.next());
			}
		} else if (function==SUM) {
			result = 0.;
			while (arguments.hasNext()) {
				result = result + arguments.next();
			}
		} else if (function==AVERAGE) {
			result = 0.;
			int nb = 0;
			while (arguments.hasNext()) {
				result = result + arguments.next();
				nb++;
			}
			result = result/nb;
		} else if (function==LN) {
			result = Math.log(arguments.next());
		} else if (function==LOG) {
			result = Math.log10(arguments.next());
		} else {
			result = super.evaluate(function, arguments);
		}
		errIfNaN(result, function);
		return result;
	}
	
	private void errIfNaN(Double result, Function function) {
		if (result.equals(Double.NaN)) throw new IllegalArgumentException("Invalid argument passed to "+function.getName());
	}
}
