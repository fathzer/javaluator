package examples;

import java.util.BitSet;
import java.util.Iterator;

import net.astesana.javaluator.*;

/** An example of how to implement an evaluator from scratch, working on something more complex than doubles.
 * <br>This evaluator computes expressions that use boolean sets.
 * <br>A boolean set is a vector of booleans. A true is represented by a one, a false, by a zero.
 * For instance "01" means {false, true}
 * <br>The evaluator uses the BitSet java class to represent these vectors.
 * <br>It supports the logical OR (+), AND (*) and NEGATE (-) operators.
 */
public class BooleanSetEvaluator extends AbstractEvaluator<BitSet> {
	/** The negate unary operator.*/
	public final static Operator NEGATE = new Operator("-", 1, Operator.Associativity.RIGHT, 3);
	/** The logical AND operator.*/
	private static final Operator AND = new Operator("*", 2, Operator.Associativity.LEFT, 2);
	/** The logical OR operator.*/
	public final static Operator OR = new Operator("+", 2, Operator.Associativity.LEFT, 1);
	/** The true constant. */
	public final static Constant TRUE = new Constant("true");
	/** The false constant. */
	public final static Constant FALSE = new Constant("false");

	/** The evalaluator's parameters.*/
	private static final Parameters PARAMETERS;
	static {
		// Create the evaluator's parameters
		PARAMETERS = new Parameters();
		// Add the supported operators
		PARAMETERS.add(AND);
		PARAMETERS.add(OR);
		PARAMETERS.add(NEGATE);
		PARAMETERS.add(TRUE);
		PARAMETERS.add(FALSE);
	}

	/** The bitset length. */
	private int length;

	/** Constructor.
	 * @param length The bitset's length.
	 */
	public BooleanSetEvaluator(int length) {
		super(PARAMETERS);
		this.length = length;
	}

	@Override
	protected BitSet toValue(String literal) {
		// A literal is composed of 0 and 1 characters. If not, it is an illegal argument
		if (literal.length()!=this.length) throw new IllegalArgumentException(literal+" must have a length of "+this.length);
		BitSet result = new BitSet(length);
		for (int i = 0; i < length; i++) {
			if (literal.charAt(i)=='1') {
				result.set(i);
			} else if (literal.charAt(i)!='0') {
				throw new IllegalArgumentException(literal+" contains the wrong character "+literal.charAt(i));
			}
		}
		return result;
	}

	@Override
	protected BitSet evaluate(Operator operator, Iterator<BitSet> operands) {
		// Implementation of supported operators
		BitSet o1 = operands.next();
		if (operator == NEGATE) {
			o1.flip(0, length);
		} else {
			BitSet o2 = operands.next();
			if (operator == OR) {
				o1.or(o2);
			} else if (operator == AND) {
				o1.and(o2);
			} else {
				o1 = super.evaluate(operator, operands);
			}
		}
		return o1;
	}
	
	@Override
	protected BitSet evaluate(Constant constant) {
		// Implementation of supported constants
		BitSet result;
		if (constant==FALSE) {
			result = new BitSet(length);
		} else if (constant==TRUE) {
			result = new BitSet(length);
			result.flip(0, length);
		} else {
			result = super.evaluate(constant);
		}
		return result;
	}
	
	/** Converts a bit set to its String representation.
	 * @param set The set to be converted.
	 * @return a String
	 */
	public String toString(BitSet set) {
		StringBuilder builder = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			builder.append(set.get(i)?'1':'0');
		}
		return builder.toString();
	}

	/** A simple program using this evaluator. */
	public static void main(String[] args) {
		BooleanSetEvaluator evaluator = new BooleanSetEvaluator(4);
		doIt(evaluator, "0011 * 1010");
		doIt(evaluator, "true * 1100");
		doIt(evaluator, "-true");
	}

	private static void doIt(BooleanSetEvaluator evaluator, String expression) {
		BitSet result = evaluator.evaluate(expression);
		System.out.println (expression+" = "+evaluator.toString(result));
	}
}
