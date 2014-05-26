package com.fathzer.soft.javaluator;

import java.util.Iterator;

public interface AbstractTokenizer {
	/** Converts an expression into tokens.
	 * <br>Example: The result for the expression "<i>-1+min(10,3)</i>" is an iterator on "-", "1", "+", "min", "(", "10", ",", "3", ")".
	 * <br>By default, the operators symbols, the brackets and the function argument separator are used as delimiter in the string.
	 * @param expression The expression that is evaluated
	 * @return A string iterator.
	 */
	public Iterator<String> tokenize(String expression);
}
