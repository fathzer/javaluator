package net.astesana.javaluator;

import java.util.ArrayList;
import java.util.Collection;

/** The parameters of an evaluator.
 * <br>An evaluator may have different parameters as the supported operators, the supported functions, etc ...
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public class Parameters {
	private boolean isSpaceIgnored;
	private final ArrayList<Operator> operators;
	private final ArrayList<Function> functions;
	private final ArrayList<Constant> constants;

	/** Constructor.
	 * <br>This method builds an instance with no operators, no functions, no constants
	 * <br>isSpaceIgnored is set to true.
	 * @see #setSpaceIgnored(boolean)
	 */
	public Parameters() {
		this.isSpaceIgnored = true;
		this.operators = new ArrayList<Operator>();
		this.functions = new ArrayList<Function>();
		this.constants = new ArrayList<Constant>();
	}

	/** Gets the supported operators.
	 * @return a Collection of operators.
	 */
	public Collection<Operator> getOperators() {
		return this.operators;
	}

	/** Gets the supported functions.
	 * @return a Collection of functions.
	 */
	public Collection<Function> getFunctions() {
		return this.functions;
	}
	
	/** Gets the supported constants.
	 * @return a Collection of constants.
	 */
	public Collection<Constant> getConstants() {
		return this.constants;
	}
	
	/** Tests whether space characters are ignored or not.
	 * @return true if space are ignored.
	 */
	public boolean isSpaceIgnored() {
		return isSpaceIgnored;
	}
	
	/** Sets the ignoreSpaces parameter.
	 * @param ignored If true, the spaces in evaluated expressions are ignored.
	 * <br>This mean that, if + is an operator, an expression like "a +b" will be considered as "a+b".
	 *  "a b" will be considered as an error (two literals without any operator).
	 */
	public void setSpaceIgnored(boolean ignored) {
		this.isSpaceIgnored = ignored;
	}

	/** Adds operators to the supported ones.
	 * @param operators The operators to be added.
	 */
	public void addOperators(Collection<Operator> operators) {
		this.operators.addAll(operators);
	}
	
	/** Adds an operator to the supported ones.
	 * @param operator The added operator
	 */
	public void add(Operator operator) {
		this.operators.add(operator);
	}

	/** Adds functions to the supported ones.
	 * @param functions The functions to be added.
	 */
	public void addFunctions(Collection<Function> functions) {
		this.functions.addAll(functions);
	}

	/** Adds a function to the supported ones.
	 * @param function The added function
	 */
	public void add(Function function) {
		this.functions.add(function);
	}

	/** Adds constants to the supported ones.
	 * @param constants The constants to be added.
	 */
	public void addConstants(Collection<Constant> constants) {
		this.constants.addAll(constants);
	}
	
	/** Adds a constant to the supported ones.
	 * @param constant The added constant
	 */
	public void add(Constant constant) {
		this.constants.add(constant);
	}
}
