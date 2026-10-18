package com.fathzer.soft.javaluator;

/** An <a href="http://en.wikipedia.org/wiki/Operator_(mathematics)">operator</a>.
 */
public class Operator {
	/** An Operator's <a href="http://en.wikipedia.org/wiki/Operator_associativity">associativity</a>.
	 */
	public enum Associativity {
		/** Left associativity.
		 * <br>For unary operators, left associativity also means the operator is a postfix operator
		 * (it appears after its operand, like the factorial operator in <i>n!</i>).*/
		LEFT,
		/** Right associativity.
		 * <br>For unary operators, right associativity also means the operator is a prefix operator
		 * (it appears before its operand, like the unary minus in <i>-x</i>).*/
		RIGHT,
		/** No associativity.*/
		NONE
	}
	private final String symbol;
	private final int precedence;
	private final int operandCount;
	private final Associativity associativity;

	/** Constructor.
	 * @param symbol The operator name (Currently, the name's length must be one character). 
	 * @param operandCount The number of operands of the operator (must be 1 or 2).
	 * @param associativity The associativity of the operator.
	 * <br>For a unary operator, the associativity also defines the position of the operator relative to its operand:
	 * {@link Associativity#RIGHT} means the operator is a prefix operator (for instance <i>-</i> in <i>-x</i>),
	 * {@link Associativity#LEFT} means it is a postfix operator (for instance <i>!</i> in <i>n!</i>).
	 * @param precedence The <a href="http://en.wikipedia.org/wiki/Order_of_operations">precedence</a> of the operator.
	 * <br>The precedence is the priority of the operator. An operator with an higher precedence will be executed before an operator with a lower precedence.
	 * Example : In "<i>1+3*4</i>" * has a higher precedence than +, so the expression is interpreted as 1+(3*4).
	 * @throws IllegalArgumentException if operandCount if not 1 or 2, or if associativity is {@link Associativity#NONE} or null, or if symbol is null, empty
	 * or starts/ends with a space character.
	 */
	public Operator(String symbol, int operandCount, Associativity associativity, int precedence) {
		if (symbol==null || associativity==null) {
			throw new IllegalArgumentException("Operator symbol and associativity can't be null");
		}
		if (!symbol.trim().equals(symbol)) {
			throw new IllegalArgumentException("Operator can't start or end by a blank char");
		}
		if (symbol.isEmpty()) {
			throw new IllegalArgumentException("Operator symbol can't be empty");
		}
		if ((operandCount<1) || (operandCount>2)) {
			throw new IllegalArgumentException("Only unary and binary operators are supported");
		}
		if (Associativity.NONE.equals(associativity)) {
			throw new IllegalArgumentException("None associativity operators are not supported");
		}
		this.symbol = symbol;
		this.operandCount = operandCount;
		this.associativity = associativity;
		this.precedence = precedence;
	}

	/** Gets the operator's symbol.
	 * @return a String
	 */
	public String getSymbol() {
		return this.symbol;
	}

	/** Gets the operator's operand count. 
	 * @return an integer
	 */
	public int getOperandCount() {
		return this.operandCount;
	}
	
	/** Gets this operator's associativity.
	 * @return true if the operator is left associative.
	 * @see <a href="http://en.wikipedia.org/wiki/Operator_associativity">Operator's associativity in Wikipedia</a>
	 */
	public Associativity getAssociativity() {
		return this.associativity;
	}
	
	/** Gets the operator's precedence.
	 * @return an integer
	 * @see <a href="http://en.wikipedia.org/wiki/Order_of_operations">Operator's associativity in Wikipedia</a>
	 */
	public int getPrecedence() {
		return this.precedence;
	}
	
	/** Tests whether this operator is a postfix unary operator.
	 * <br>A unary operator is a postfix one (it appears after its operand, like <i>!</i> in <i>n!</i>) if it is
	 * left associative. If it is right associative, it is a prefix operator (it appears before its operand,
	 * like <i>-</i> in <i>-x</i>).
	 * @return true if this operator is a left associative unary operator (always false for binary operators).
	 */
	public boolean isPostfix() {
		return this.operandCount==1 && Associativity.LEFT.equals(this.associativity);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + operandCount;
		result = prime * result + associativity.hashCode();
		result = prime * result + symbol.hashCode();
		result = prime * result + precedence;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Operator)) {
			return false;
		}
		final Operator other = (Operator) obj;
		return associativity==other.associativity && operandCount==other.operandCount && symbol.equals(other.symbol) && precedence==other.precedence;
	}
}