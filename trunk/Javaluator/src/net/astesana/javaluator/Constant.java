package net.astesana.javaluator;

/**
 * A constant in an expression.
 * <br>Some expressions needs constants. For instance it is impossible to perform trigonometric calculus without using pi.
 * A constant allows you to use mnemonic in your expressions instead of the raw value of the constant.
 * <br>A constant for pi would be defined by :<br>
 * <code>Constant<Double> pi = new Constant<Double>("pi", Math.PI);</code>
 * <br>With such a constant, you will be able to evaluate the expression "sin(pi/4)"
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 * @see AbstractEvaluator#evaluate(Constant)
 */
public class Constant {
	private String mnemonic;
	
	/** Constructor
	 * @param mnemonic The mnemonic of the constant.
	 * <br>The mnemonic is used in expressions to identified the constants.
	 */
	public Constant(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	/** Gets the mnemonic of the constant.
	 * @return the id
	 */
	public String getMnemonic() {
		return mnemonic;
	}
}
