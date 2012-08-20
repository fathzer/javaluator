package examples;

import net.astesana.javaluator.*;

/** An example of how to localize the function names in an existing evaluator.
 * <br>As a French, I prefer "moyenne" to "avg" and "somme" to "sum". Here is how I can do that very easily.
 */
public class LocalizedDoubleEvaluator {
	
	public static void main(String[] args) {
		// Gets the default DoubleEvaluator's parameters
		Parameters params = DoubleEvaluator.getDefaultParameters();
		// adds the translations
		params.setTranslation(DoubleEvaluator.SUM, "somme");
		params.setTranslation(DoubleEvaluator.AVERAGE, "moyenne");
		
		// Create a new DoubleEvaluator that support the translations
		DoubleEvaluator evaluator = new DoubleEvaluator(params);
		
		// Test that all this stuff is ok
		String expression = "moyenne(3,somme(1,7,-3))";
		System.out.println (expression+" = "+evaluator.evaluate(expression));
	}
}
