package examples;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import net.astesana.javaluator.*;

/** An example of how to localize an existing evaluator to match French locale.
 * <br>As a French, I prefer "moyenne" to "avg" and "somme" to "sum".
 * <br>As the default argument function (',') is used as decimal separator in France,
 * I may also change it to ';'.
 * <br> Here is how I can do that very easily.
 */
public class Localizing {
	public static void main(String[] args) {
		// Gets the default DoubleEvaluator's parameters
		Parameters params = DoubleEvaluator.getDefaultParameters();
		// adds the translations
		params.setTranslation(DoubleEvaluator.SUM, "somme");
		params.setTranslation(DoubleEvaluator.AVERAGE, "moyenne");
		// Change the default function separator
		params.setFunctionArgumentSeparator(';');
		
		// Create a French number formatter
		final DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRENCH);
		format.setGroupingUsed(true);
		// Create a new DoubleEvaluator that support the translations and the French decimal separator
		DoubleEvaluator evaluator = new DoubleEvaluator(params) {
			@Override
			protected Double toValue(String literal, Object evaluationContext) {
				// Override the method that converts a literal to a number, in order to match with
				// the French decimal separator
				try {
					// For a strange reason, Java thinks that only non breaking spaces are French thousands
					// separators. So, we will replace spaces in the literal by non breaking spaces
					literal = literal.replace(' ', (char)0x00A0); 
					return format.parse(literal).doubleValue();
				} catch (ParseException e) {
					// If the number has a wrong format, throw the right exception.
					throw new IllegalArgumentException(literal+" is not a number");
				}
			}
		};
		
		// Test that all this stuff is ok
		String expression = "3 000 +moyenne(3 ; somme(1,5 ; 7 ; -3,5))";
		System.out.println (expression+" = "+format.format(evaluator.evaluate(expression)));
	}
}
