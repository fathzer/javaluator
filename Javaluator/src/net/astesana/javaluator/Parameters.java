package net.astesana.javaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** The parameters of an evaluator.
 * <br>An evaluator may have different parameters as the supported operators, the supported functions, etc ...
 * @author Jean-Marc Astesana
 * @see <a href="../../../license.html">License information</a>
 */
public class Parameters {
	private String functionSeparator;
	private final ArrayList<Operator> operators;
	private final ArrayList<Function> functions;
	private final ArrayList<Constant> constants;
	private final Map<String, String> translations;

	/** Constructor.
	 * <br>This method builds an instance with no operators, no functions, no constants
	 * <br>Function argument separator is set to ','.
	 * @see #setSpaceIgnored(boolean)
	 */
	public Parameters() {
		this.operators = new ArrayList<Operator>();
		this.functions = new ArrayList<Function>();
		this.constants = new ArrayList<Constant>();
		this.translations = new HashMap<String, String>();
		setFunctionArgumentSeparator(',');
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
	
	/** Sets the translated term for a function.
	 * <br>Using this method, you can localize the names of some built-in functions. For instance,
	 * for french people,you can use this method to use "somme" instead of "sum" with the SUM built-in
	 * function of DoubleEvaluator. 
	 * @param function The function you want to translate the name
	 * @param translatedName The translated name
	 * @see DoubleEvaluator#SUM
	 */
	public void setTranslation(Function function, String translatedName) {
		setTranslation(function.getName(), translatedName);
	}

	/** Sets the translated term for a constant.
	 * @param constant The constant you want to translate the name
	 * @param translatedName The translated name
	 * @see #setTranslation(Function, String)
	 */
	public void setTranslation(Constant constant, String translatedName) {
		setTranslation(constant.getName(), translatedName);
	}
	
	private void setTranslation(String name, String translatedName) {
		this.translations.put(name, translatedName);
	}
	
	String getTranslation(String originalName) {
		String translation = this.translations.get(originalName);
		return translation==null?originalName:translation;
	}
	
	/** Sets the function argument separator.
	 * <br>Its default value is ','.
	 * @param separator The new separator
	 */
	public void setFunctionArgumentSeparator(char separator) {
		this.functionSeparator = new String(new char[]{separator});
	}
	
	/** Gets the function argument separator.
	 * @return a string
	 */
	public String getFunctionArgumentSeparator() {
		return this.functionSeparator;
	}
}
