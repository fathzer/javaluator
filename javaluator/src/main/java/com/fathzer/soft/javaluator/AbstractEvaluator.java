package com.fathzer.soft.javaluator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** An abstract evaluator, able to evaluate infix expressions.
 * <br>Some standard evaluators are included in the library, you can define your own by subclassing this class.
 * <br>This class is thread safe.
 * @param <T> The type of values handled by the evaluator 
 * @author Jean-Marc Astesana
 * @see <a href="https://opensource.org/license/apache-2-0">License information (Apache 2)</a>
 */
public abstract class AbstractEvaluator<T> {
	private final Tokenizer tokenizer;
	private final Map<String, Function> functions;
	private final Map<String, List<Operator>> operators;
	private final Map<String, Constant> constants;
	private final String functionArgumentSeparator;
	private final Map<String, BracketPair> functionBrackets;
	private final Map<String, BracketPair> expressionBrackets;
		
	/** Constructor.
	 * @param parameters The evaluator parameters.
	 * <br>Please note that there's no side effect between the evaluator and the parameters.
	 * So, changes made to the parameters after the call to this constructor are ignored by the instance. 
	 */
	protected AbstractEvaluator(Parameters parameters) {
		//TODO if constants, operators, functions are duplicated => error
		final List<String> tokenDelimitersBuilder = new ArrayList<>();
		this.functions = new HashMap<>();
		this.operators = new HashMap<>();
		this.constants = new HashMap<>();
		this.functionBrackets = new HashMap<>();
		for (final BracketPair pair : parameters.getFunctionBrackets()) {
			functionBrackets.put(pair.getOpen(), pair);
			functionBrackets.put(pair.getClose(), pair);
			tokenDelimitersBuilder.add(pair.getOpen());
			tokenDelimitersBuilder.add(pair.getClose());
		}
		this.expressionBrackets = new HashMap<>();
		for (final BracketPair pair : parameters.getExpressionBrackets()) {
			expressionBrackets.put(pair.getOpen(), pair);
			expressionBrackets.put(pair.getClose(), pair);
			tokenDelimitersBuilder.add(pair.getOpen());
			tokenDelimitersBuilder.add(pair.getClose());
		}
		if (operators!=null) {
			for (Operator ope : parameters.getOperators()) {
				tokenDelimitersBuilder.add(ope.getSymbol());
				List<Operator> known = this.operators.get(ope.getSymbol());
				if (known==null) {
					known = new ArrayList<>();
					this.operators.put(ope.getSymbol(), known);
				}
				known.add(ope);
				if (known.size()>1) {
					validateHomonyms(known);
				}
			}
		}
		boolean needFunctionSeparator = false;
		if (parameters.getFunctions()!=null) {
			for (Function function : parameters.getFunctions()) {
				this.functions.put(parameters.getTranslation(function.getName()), function);
				if (function.getMaximumArgumentCount()>1) {
					needFunctionSeparator = true;
				}
			}			
		}
		if (parameters.getConstants()!=null) {
			for (Constant constant : parameters.getConstants()) {
				this.constants.put(parameters.getTranslation(constant.getName()), constant);
			}
		}
		functionArgumentSeparator = parameters.getFunctionArgumentSeparator();
		if (needFunctionSeparator) {
			tokenDelimitersBuilder.add(functionArgumentSeparator);
		}
		tokenizer = new Tokenizer(tokenDelimitersBuilder);
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
		if (operators.size()>2) {
			throw new IllegalArgumentException();
		}
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
			if (operator.getOperandCount()==argCount) {
				return operator;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private void output(Deque<T> values, Token token, Object evaluationContext) {
		if (token.isLiteral()) { // If the token is a literal, a constant, or a variable name
			String literal = token.getLiteral();
			Constant ct = this.constants.get(literal);
			T value = ct==null?null:evaluate(ct, evaluationContext);
			if (value==null && evaluationContext!=null && (evaluationContext instanceof AbstractVariableSet)) {
				value = ((AbstractVariableSet<T>)evaluationContext).get(literal);
			}
			values.push(value!=null ? value : toValue(literal, evaluationContext));
		} else if (token.isOperator()) {
			Operator operator = token.getOperator();
			values.push(evaluate(operator, getArguments(values, operator.getOperandCount()), evaluationContext));
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Evaluates a constant.
	 * <br>Subclasses that support constants must override this method (The default implementation throws a UnsupportedOperationException).
	 * @param constant The constant
	 * @param evaluationContext The context of the evaluation
	 * @return The constant's value
	 * @throws UnsupportedOperationException if implementor of a subclass defined constants but forgot to override this method 
	 */
	protected T evaluate(Constant constant, Object evaluationContext) {
		throw new UnsupportedOperationException("evaluate(Constant) is not implemented for "+constant.getName());
	}
	
	/** Evaluates an operation.
	 * <br>Subclasses that support operators must override this method (The default implementation throws a UnsupportedOperationException).
	 * @param operator The operator
	 * @param operands The operands
	 * @param evaluationContext The context of the evaluation
	 * @return The result of the operation
	 * @throws UnsupportedOperationException if implementor of a subclass defined operators but forgot to override this method 
	 */
	protected T evaluate(Operator operator, Iterator<T> operands, Object evaluationContext) {
		throw new UnsupportedOperationException("evaluate(Operator, Iterator) is not implemented for "+operator.getSymbol());
	}
	
	/** Evaluates a function.
	 * <br>Subclasses that support functions must override this method (The default implementation throws a UnsupportedOperationException).
	 * @param function The function
	 * @param arguments The function's arguments
	 * @param evaluationContext The context of the evaluation
	 * @return The result of the function
	 * @throws UnsupportedOperationException if implementor of a subclass defined functions but forgot to override this method 
	 */
	protected T evaluate(Function function, Iterator<T> arguments, Object evaluationContext) {
		throw new UnsupportedOperationException("evaluate(Function, Iterator) is not implemented for "+function.getName());
	}
	
	private void doFunction(Deque<T> values, Function function, int argCount, Object evaluationContext) {
		if (function.getMinimumArgumentCount()>argCount || function.getMaximumArgumentCount()<argCount) {
			throw new IllegalArgumentException("Invalid argument count for "+function.getName());
		}
		values.push(evaluate(function, getArguments(values, argCount), evaluationContext));
	}
	
	private Iterator<T> getArguments(Deque<T> values, int nb) {
		// Be aware that arguments are in reverse order on the values stack.
		// Don't forget to reorder them in the original order (the one they appear in the evaluated formula)
		if (values.size()<nb) {
			throw new IllegalArgumentException();
		}
		LinkedList<T> result = new LinkedList<>();
		for (int i = 0; i <nb ; i++) {
			result.addFirst(values.pop());
		}
		return result.iterator();
	}
	
	/** Evaluates a literal (Converts it to a value).
	 * @param literal The literal to evaluate.
	 * @return an instance of T.
	 * @param evaluationContext The context of the evaluation
	 * @throws IllegalArgumentException if the literal can't be converted to a value.
	 */
	protected abstract T toValue(String literal, Object evaluationContext);
	
	/** Evaluates an expression.
	 * @param expression The expression to evaluate.
	 * @return the result of the evaluation.
	 * @throws IllegalArgumentException if the expression is not correct.
	 */
	public T evaluate(String expression) {
		return evaluate(expression, null);
	}
	
	/** Evaluates an expression that contains variables.
	 * @param expression The expression to evaluate.
	 * @param evaluationContext The context of the evaluation.
	 * <br>This context is an object that can contain useful dynamic data, for example the values of the variables
	 * used in the expression (Use an AbstractVariableSet to do that).<br>The context is not limited to variable values but
	 * can be used for any dynamic information. A good example is the <a href="http://javaluator.sourceforge.net/en/doc/tutorial.php?chapter=creatingComplex">BooleanSetEvaluator</a> one.
	 * @return the result of the evaluation.
	 * @throws IllegalArgumentException if the expression is not correct.
	 * @see AbstractVariableSet
	 */
	public T evaluate(String expression, Object evaluationContext) {
		final Deque<T> values = new ArrayDeque<>(); // values stack
		final Deque<Token> stack = new ArrayDeque<>(); // operator stack
		final Deque<Integer> previousValuesSize = functions.isEmpty()?null:new ArrayDeque<>();
		final Iterator<String> tokens = tokenize(expression);
		Token previous = null;
		while (tokens.hasNext()) {
			// read one token from the input stream
			String strToken = tokens.next();
			final Token token = toToken(previous, strToken);
			if (token.isOpenBracket()) {
				// If the token is a left parenthesis, then push it onto the stack.
				stack.push(token);
				if (previous!=null && previous.isFunction()) {
					if (!functionBrackets.containsKey(token.getBrackets().getOpen())) {
						throw new IllegalArgumentException("Invalid bracket after function: "+strToken);
					}
				} else {
					if (!expressionBrackets.containsKey(token.getBrackets().getOpen())) {
						throw new IllegalArgumentException("Invalid bracket in expression: "+strToken);
					}
				}
			} else if (token.isCloseBracket()) {
				if (previous==null) {
					throw new IllegalArgumentException("expression can't start with a close bracket");
				}
				if (previous.isFunctionArgumentSeparator()) {
					throw new IllegalArgumentException("argument is missing");
				}
				BracketPair brackets = token.getBrackets();
				// If the token is a right parenthesis:
				boolean openBracketFound = false;
				// Until the token at the top of the stack is a left parenthesis,
				// pop operators off the stack onto the output queue
				while (!stack.isEmpty()) {
					Token sc = stack.pop();
					if (sc.isOpenBracket()) {
						if (sc.getBrackets().equals(brackets)) {
							openBracketFound = true;
							break;
						} else {
							throw new IllegalArgumentException("Invalid parenthesis match "+sc.getBrackets().getOpen()+brackets.getClose());
						}
					} else {
						output(values, sc, evaluationContext);
					}
				}
				if (!openBracketFound) {
					// If the stack runs out without finding a left parenthesis, then
					// there are mismatched parentheses.
					throw new IllegalArgumentException("Parentheses mismatched");
				}
				if (!stack.isEmpty() && stack.peek().isFunction()) {
					// If the token at the top of the stack is a function token, pop it
					// onto the output queue.
					int argCount = values.size()-previousValuesSize.pop();
					doFunction(values, stack.pop().getFunction(), argCount, evaluationContext);
				}
			} else if (token.isFunctionArgumentSeparator()) {
				if (previous==null) {
					throw new IllegalArgumentException("expression can't start with a function argument separator");
				}
				// Verify that there was an argument before this separator
				if (previous.isOpenBracket() || previous.isFunctionArgumentSeparator()) {
					// The cases were operator miss an operand are detected elsewhere.
					throw new IllegalArgumentException("argument is missing");
				}
				// If the token is a function argument separator
				boolean pe = false;
				while (!stack.isEmpty()) {
					if (stack.peek().isOpenBracket()) {
						pe = true;
						break;
					} else {
						// Until the token at the top of the stack is a left parenthesis,
						// pop operators off the stack onto the output queue.
						output(values, stack.pop(), evaluationContext);
					}
				}
				if (!pe) {
					// If no left parentheses are encountered, either the separator was misplaced
					// or parentheses were mismatched.
					throw new IllegalArgumentException("Separator or parentheses mismatched");
				} else {
					// Verify we are in function scope
					Token openBracket = stack.pop();
					Token scopeToken = stack.peek();
					stack.push(openBracket);
					if (!scopeToken.isFunction()) {
						throw new IllegalArgumentException("Argument separator used outside of function scope");
					}
				}
			} else if (token.isFunction()) {
				// If the token is a function token, then push it onto the stack.
				stack.push(token);
				previousValuesSize.push(values.size());
			} else if (token.isOperator()) {
				// If the token is an operator, op1, then:
				while (!stack.isEmpty()) {
					Token sc = stack.peek();
					// While there is an operator token, op2, at the top of the stack
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
						// Pop op2 off the stack, onto the output queue;
						output(values, stack.pop(), evaluationContext);
					} else {
						break;
					}
				}
				// push op1 onto the stack.
				stack.push(token);
			} else {
				// If the token is a number (identifier), a constant or a variable, then add its value to the output queue.
				if ((previous!=null) && previous.isLiteral()) {
					throw new IllegalArgumentException("A literal can't follow another literal");
				}
				output(values, token, evaluationContext);
			}
			previous = token;
		}
		// When there are no more tokens to read:
		// While there are still operator tokens in the stack:
		while (!stack.isEmpty()) {
			Token sc = stack.pop();
			if (sc.isOpenBracket() || sc.isCloseBracket()) {
				throw new IllegalArgumentException("Parentheses mismatched");
			}
			output(values, sc, evaluationContext);
		}
		if (values.size()!=1) {
			throw new IllegalArgumentException();
		}
		return values.pop();
	}

	private Token toToken(Token previous, String token) {
		if (token.equals(functionArgumentSeparator)) {
			return Token.FUNCTION_ARG_SEPARATOR;
		} else if (functions.containsKey(token)) {
			return Token.buildFunction(functions.get(token));
		} else if (operators.containsKey(token)) {
			List<Operator> list = operators.get(token);
			return (list.size()==1) ? Token.buildOperator(list.get(0)) : Token.buildOperator(guessOperator(previous, list));
		} else {
			final BracketPair brackets = getBracketPair(token);
			if (brackets!=null) {
				if (brackets.getOpen().equals(token)) {
					return Token.buildOpenToken(brackets);
				} else {
					return Token.buildCloseToken(brackets);
				}
			} else {
				return Token.buildLiteral(token);
			}
		}	
	}

	private BracketPair getBracketPair(String token) {
		BracketPair result = expressionBrackets.get(token);
		return result==null ? functionBrackets.get(token) : result;
	}
	
	/** Gets the operators supported by this evaluator.
	 * @return a collection of operators.
	 */
	public Collection<Operator> getOperators() {
		ArrayList<Operator> result = new ArrayList<>();
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

	/** Converts the evaluated expression into tokens.
	 * <br>Example: The result for the expression "<i>-1+min(10,3)</i>" is an iterator on "-", "1", "+", "min", "(", "10", ",", "3", ")".
	 * <br>By default, the operators symbols, the brackets and the function argument separator are used as delimiter in the string.
	 * @param expression The expression that is evaluated
	 * @return A string iterator.
	 */
	protected Iterator<String> tokenize(String expression) {
		return tokenizer.tokenize(expression);
	}
}
