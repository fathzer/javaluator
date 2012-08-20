package net.astesana.javaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

/** An abstract evaluator, able to evaluate infix expressions.
 * <Some standard evaluators are included in the library, you can define your own by subclassing this class.
 * @param <T> The type of values handled by the evaluator 
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public abstract class AbstractEvaluator<T> {
	private static final String FUNCTION_ARGUMENT_SEPARATOR = ",";
	private static final String CLOSE_BRACKET = ")";
	private static final String OPEN_BRACKET = "(";
	
	private final String tokenDelimiters;
	private final Map<String, Function> functions;
	private final Map<String, List<Operator>> operators;
	private final Map<String, Constant> constants;
	
	/** Constructor.
	 * @param operators The operators supported by this evaluator.
	 * @param functions The functions supported by this evaluator.
	 * @param constants The constants supported by this evaluator.
	 * @param ignoreSpaces If true, the spaces in evaluated expressions are ignored.
	 * <br>This mean that, if + is an operator, an expression like "a +b" will be considered as "a+b".
	 *  "a b" will be considered as an error (two literals without any operator).
	 */
	protected AbstractEvaluator(Operator[] operators, Function[] functions, Constant[] constants, boolean ignoreSpaces) {
		//TODO if constants, operators, functions are duplicated => error
		this.functions = new HashMap<String, Function>();
		this.operators = new HashMap<String, List<Operator>>();
		this.constants = new HashMap<String, Constant>();
		final StringBuilder tokenDelimitersBuilder = new StringBuilder();
		tokenDelimitersBuilder.append(OPEN_BRACKET).append(CLOSE_BRACKET);
		if (ignoreSpaces) tokenDelimitersBuilder.append(" ");
		if (operators!=null) {
			for (Operator ope : operators) {
				tokenDelimitersBuilder.append(ope.getSymbol());
				List<Operator> known = this.operators.get(ope.getSymbol());
				if (known==null) {
					known = new ArrayList<Operator>();
					this.operators.put(ope.getSymbol(), known);
				}
				known.add(ope);
				if (known.size()>1) validateHomonyms(known);
			}
		}
		boolean needFunctionSeparator = false;
		if (functions!=null && functions.length>0) {
			for (Function function : functions) {
				//TODO if function name contains operators or reserved chars => error
				this.functions.put(function.getName(), function);
				if (function.getMaximumArgumentCount()>1) needFunctionSeparator = true;
			}			
		}
		if (constants!=null && constants.length!=0) {
			for (Constant constant : constants) {
				this.constants.put(constant.getMnemonic(), constant);
			}
		}
		if (needFunctionSeparator) tokenDelimitersBuilder.append(FUNCTION_ARGUMENT_SEPARATOR);
		tokenDelimiters = tokenDelimitersBuilder.toString();
	}
	
	/** Validates that homonym operators are valid.
	 * <br>Homonym operators are operators with the same name (like the unary - and the binary - operators)
	 * <br>This method is called when homonyms are passed to the constructor.
	 * <br>This default implementation only allows the case where there's two operators, one binary and one unary.
	 * Subclasses can override this method in order to accept others configurations. 
	 * @param operators The operators to validate.
	 * @throws IllegalArgumentException if the homonyms are not compatibles.
	 * @see #guessOperator(Token, List)
	 */
	protected void validateHomonyms(List<Operator> operators) {
		if (operators.size()>2) throw new IllegalArgumentException();
	}
	
	/** When a token can be more than one operator (homonym operators), this method guesses the right operator.
	 * <br>A very common case is the - sign in arithmetic computation which can be an unary or a binary operator, depending
	 * on what was the previous token. 
	 * <br><b>Warning:</b> maybe the arguments of this function are not enough to deal with all the cases.
	 * So, this part of the evaluation is in alpha state (method may change in the future).
	 * @param previous The last parsed tokens (the previous token in the infix expression we are evaluating). 
	 * @param candidates The candidate tokens.
	 * @return A token
	 * @see #validateHomonyms(List)
	 */
	protected Operator guessOperator(Token previous, List<Operator> candidates) {
		final int argCount = ((previous!=null) && (previous.isCloseBracket() || previous.isLiteral())) ? 2 : 1;
		for (Operator operator : candidates) {
			if (operator.getOperandCount()==argCount) return operator;
		}
		return null;
	}

	private void output(Stack<T> values, Token token) {
		if (token.isLiteral()) {
			String literal = token.getLiteral();
			Constant ct = this.constants.get(literal); 
			values.push(ct!=null ? evaluate(ct) : toValue(literal));
		} else if (token.isOperator()) {
			Operator operator = token.getOperator();
			values.push(evaluate(operator, getArguments(values, operator.getOperandCount())));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Evaluates a constant.
	 * <br>Subclasses that support constants must override this method.
	 * The default implementation throws a RuntimeException meaning that implementor forget to implement this method
	 * while creating a subclass that accepts constants.
	 * @param constant The constant
	 * @return The constant's value
	 */
	protected T evaluate(Constant constant) {
		throw new RuntimeException("evaluate(Constant) is not implemented for "+constant.getMnemonic());
	}
	
	/** Evaluates an operation.
	 * <br>Subclasses that support operators must override this method.
	 * The default implementation throws a RuntimeException meaning that implementor forget to implement this method
	 * while creating a subclass that accepts operators.
	 * @param operator The operator
	 * @param operands The operands
	 * @return The result of the operation
	 */
	protected T evaluate(Operator operator, Iterator<T> operands) {
		throw new RuntimeException("evaluate(Operator, Iterator) is not implemented for "+operator.getSymbol());
	}
	
	/** Evaluates a function.
	 * <br>Subclasses that support functions must override this method.
	 * The default implementation throws a RuntimeException meaning that implementor forget to implement this method
	 * while creating a subclass that accepts functions.
	 * @param function The function
	 * @param arguments The function's arguments
	 * @return The result of the function
	 */
	protected T evaluate(Function function, Iterator<T> arguments) {
		throw new RuntimeException("evaluate(Function, Iterator) is not implemented for "+function.getName());
	}
	
	private void doFunction(Stack<T> values, Function function, int argCount) {
		if (function.getMinimumArgumentCount()>argCount || function.getMaximumArgumentCount()<argCount) {
			throw new IllegalArgumentException("Invalid argument count for "+function.getName());
		}
		values.push(evaluate(function,getArguments(values, argCount)));
	}
	
	private Iterator<T> getArguments(Stack<T> values, int nb) {
		// Be aware that arguments are in reverse order on the values stack.
		// Don't forget to reorder them in the original order (the one they appear in the evaluated formula)
		if (values.size()<nb) throw new IllegalArgumentException();
		LinkedList<T> result = new LinkedList<T>();
		for (int i = 0; i <nb ; i++) {
			result.addFirst(values.pop());
		}
		return result.iterator();
	}
	
	/** Evaluates a literal (Converts it to a value).
	 * @param literal The literal to evaluate.
	 * @return an instance of T.
	 * @throws IllegalArgumentException if the literal can't be converted to a value.
	 */
	protected abstract T toValue(String literal);
	
	private Enumeration<String> tokenize(String expression) {
		final StringTokenizer tokens = new StringTokenizer(expression, tokenDelimiters, true);
		return new Enumeration<String>() {
			public boolean hasMoreElements() {
				return tokens.hasMoreElements();
			}
			public String nextElement() {
				return tokens.nextToken();
			}
		};
	}

	/** Evaluates an expression.
	 * @param expression The expression to evaluate.
	 * @return the result of the evaluation.
	 * @throws IllegalArgumentException if the expression is not correct.
	 */
	public T evaluate(String expression) {
		final Stack<T> values = new Stack<T>(); // values stack
		final Stack<Token> stack = new Stack<Token>(); // operator stack
		final Stack<Integer> functionArgCount = new Stack<Integer>(); // function argument count stack
		final Stack<Boolean> wereValues = new Stack<Boolean>();
		final Enumeration<String> tokens = tokenize(expression);
		Token previous = null;
		while (tokens.hasMoreElements()) {
			// read one token from the input stream
			final Token token = toToken(previous, tokens.nextElement());
			if (token.isIgnored()) {
				// If the token is a space ... do nothing
			} else {
				if (token.isOpenBracket()) {
					// If the token is a left parenthesis, then push it onto the stack.
					stack.push(token);
				} else if (token.isCloseBracket()) {
					// If the token is a right parenthesis:
					boolean openBracketFound = false;
					// Until the token at the top of the stack is a left parenthesis,
					// pop operators off the stack onto the output queue
					while (!stack.isEmpty()) {
						Token sc = stack.pop();
						if (sc.isOpenBracket()) {
							openBracketFound = true;
							break;
						} else {
							output(values, sc);
						}
					}
					if (!openBracketFound) {
						// If the stack runs out without finding a left parenthesis, then
						// there are mismatched parentheses.
						throw new IllegalArgumentException("Parentheses mismatched");
					}
					// If the token at the top of the stack is a function token, pop it
					// onto the output queue.
					if (!stack.isEmpty()) {
						if (stack.peek().isFunction()) {
							int argCount = functionArgCount.pop();
							if (wereValues.pop()) {
								argCount++;
							} else {
								throw new IllegalArgumentException("Function argument is missing");
							}
							doFunction(values, (Function)stack.pop().getFunction(), argCount);
						}
					}
				} else if (token.isFunctionArgumentSeparator()) {
					// If the token is a function argument separator (e.g., a comma):
					boolean pe = false;
					while (!stack.isEmpty()) {
						if (stack.peek().isOpenBracket()) {
							pe = true;
							break;
						} else {
							// Until the token at the top of the stack is a left parenthesis,
							// pop operators off the stack onto the output queue.
							output(values, stack.pop());
						}
					}
					if (!pe) {
						// If no left parentheses are encountered, either the separator was misplaced
						// or parentheses were mismatched.
						throw new IllegalArgumentException("Separator or parentheses mismatched");
					}
					if (wereValues.pop()) {
						int argCount = functionArgCount.pop();
						argCount++;
						functionArgCount.push(argCount);
					} else {
						throw new IllegalArgumentException("Function argument is missing");
					}
					wereValues.push(false);
				} else if (token.isFunction()) {
					// If the token is a function token, then push it onto the stack.
					stack.push(token);
					functionArgCount.push(0);
					if (!wereValues.isEmpty()) {
						wereValues.pop(); wereValues.push(true);
					}
					wereValues.push(true);
				} else if (token.isOperator()) {
					// If the token is an operator, op1, then:
					while (!stack.isEmpty()) {
						Token sc = stack.peek();
						// While there is an operator token, o2, at the top of the stack
						// op1 is left-associative and its precedence is less than or equal
						// to that of op2,
						// or op1 has precedence less than that of op2,
						// Let + and ^ be right associative.
						// Correct transformation from 1^2+3 is 12^3+
						// The differing operator priority decides pop / push
						// If 2 operators have equal priority then associativity decides.
						if (sc.isOperator()
								&& ((token.getAssociativity().equals(Operator.Associativity.LEFT) && (token.getPrecedence() <= sc.getPrecedence())) ||
										(token.getPrecedence() < sc.getPrecedence()))) {
							// Pop o2 off the stack, onto the output queue;
							output(values, stack.pop());
						} else {
							break;
						}
					}
					// push op1 onto the stack.
					stack.push(token);
				} else {
					// If the token is a number (identifier), then add it to the output queue.
					if ((previous!=null) && previous.isLiteral()) throw new IllegalArgumentException("A literal can follow another literal");
					if (!wereValues.isEmpty()) {
						wereValues.pop(); wereValues.push(true);
					}
					output(values, token);
				}
				previous = token;
			}
		}
		// When there are no more tokens to read:
		// While there are still operator tokens in the stack:
		while (!stack.isEmpty()) {
			Token sc = stack.pop();
			if (sc.isOpenBracket() || sc.isCloseBracket()) {
				throw new IllegalArgumentException("Parentheses mismatched");
			}
			output(values, sc);
		}
		if (values.size()!=1) throw new IllegalArgumentException();
		return values.pop();
	}

	private Token toToken(Token previous, String token) {
		if (token.equals(" ")) {
			return Token.IGNORED;
		} else if (token.equals(OPEN_BRACKET)) {
			return Token.OPEN_BRACKET;
		} else if (token.equals(CLOSE_BRACKET)) {
			return Token.CLOSE_BRACKET;
		} else if (token.equals(FUNCTION_ARGUMENT_SEPARATOR)) {
			return Token.FUNCTION_ARG_SEPARATOR;
		} else if (functions.containsKey(token)) {
			return Token.buildFunction(functions.get(token));
		} else if (operators.containsKey(token)) {
			List<Operator> list = operators.get(token);
			if (list.size()==1) return Token.buildOperator(list.get(0));
			return Token.buildOperator(guessOperator(previous, list));
		} else {
			return Token.buildLiteral(token);
		}
	}
	
	/** Gets the operators supported by this evaluator.
	 * @return a collection of operators.
	 */
	public Collection<Operator> getOperators() {
		ArrayList<Operator> result = new ArrayList<Operator>();
		Collection<List<Operator>> values = this.operators.values();
		for (List<Operator> list : values) {
			result.addAll(list);
		}
		return result;
	}

	/** Gets the functions supported by this evaluator.
	 * @return a collection of functions.
	 */
	public Collection<Function> getFunctions() {
		return this.functions.values();
	}

	/** Gets the constants supported by this evaluator.
	 * @return a collection of constants.
	 */
	public Collection<Constant> getConstants() {
		return this.constants.values();
	}
}
