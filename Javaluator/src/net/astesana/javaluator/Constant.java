package net.astesana.javaluator;

/**
 * A constant in an expression.
 * <br>Some expressions needs constants. For instance it is impossible to perform trigonometric calculus without using pi.
 * A constant allows you to use mnemonic in your expressions instead of the raw value of the constant.
 * <br>A constant for pi would be defined by :<br>
 * <code>Constant<Double> pi = new Constant<Double>("pi", Math.PI);</code>
 * <br>With such a constant, you will be able to evaluate the expression "sin(pi/4)"
 * @param <T> The value type of the constant. For example, pi is a Double. 
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public class Constant<T> {
	private T value;
	private String mnemonic;
	
	/** Constructor
	 * @param mnemonic The mnemonic of the constant.
	 * <br>The mnemonic is used in expressions to identified the constants.
	 * @param value The value of the constant
	 */
	public Constant(String mnemonic, T value) {
		this.value = value;
		this.mnemonic = mnemonic;
	}

	/** Gets the constant value.
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/** Gets the mnemonic of the constant.
	 * @return the id
	 */
	public String getMnemonic() {
		return mnemonic;
	}
}
