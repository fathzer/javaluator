package net.astesana.javaluator;

import java.util.Iterator;

/** An abstract <a href="http://en.wikipedia.org/wiki/Function_(mathematics)">function</a>.
 * @param <T> The type of arguments and results handled by the function.
 * @author Jean-Marc Astesana
 * <BR>License : <a href="http://www.gnu.org/licenses/gpl-3.0.en.html">GPL v3</a>
 */
public abstract class Function<T> {
	private String name;
	private int minArgumentCount;
	private int maxArgumentCount;

	/** Constructor.
	 * <br>This constructor builds a function with a fixed arguments count.
	 * @param name The function's name
	 * @param argumentCount The function's argument count.
	 * @throw IllegalArgumentException if argumentCount is lower than 0 or if the function name is null or empty.
	 */
	public Function(String name, int argumentCount) {
		this(name, argumentCount, argumentCount);
	}

	/** Constructor.
	 * <br>This constructor builds a function with a variable arguments count.
	 * <br>For instance, a minimum function may have at least one argument.
	 * @param name The function's name
	 * @param minArgumentCount The function's minimum argument count.
	 * @param maxArgumentCount The function's maximum argument count (Integer.MAX_VALUE to specify no upper limit).
	 * @throw IllegalArgumentException if minArgumentCount is less than 0 or greater than maxArgumentCount or if the function name is null or empty.
	 */
	public Function(String name, int minArgumentCount, int maxArgumentCount) {
		if ((minArgumentCount<0) || (minArgumentCount>maxArgumentCount)) throw new IllegalArgumentException("Invalid argument count");
		if (name==null || name.length()==0) throw new IllegalArgumentException("Invalid function name");
		this.name = name;
		this.minArgumentCount = minArgumentCount;
		this.maxArgumentCount = maxArgumentCount;
	}

	/** Gets the function's name.
	 * @return the name of the function
	 */
	public String getName() {
		return this.name;
	}

	/** Gets the function's minimum argument count.
	 * @return an integer
	 */
	public int getMinimumArgumentCount() {
		return this.minArgumentCount;
	}

	/** Gets the function's maximum argument count.
	 * @return an integer
	 */
	public int getMaximumArgumentCount() {
		return this.maxArgumentCount;
	}
	
	/** Evaluates the function's result.
	 * @param arguments The arguments of the function.
	 * <br>It is guaranteed that the number of arguments is in the range specified in the constructor.
	 * @return The result of the function.
	 */
	public abstract T evaluate(Iterator<T> arguments);
}