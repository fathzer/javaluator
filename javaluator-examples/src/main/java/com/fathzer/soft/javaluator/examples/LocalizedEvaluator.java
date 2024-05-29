package com.fathzer.soft.javaluator.examples;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Parameters;

/** An example of how to localize an existing evaluator to match French locale.
 * <br>As a French, I prefer "moyenne" to "avg" and "somme" to "sum".
 * <br>As the default argument function (',') is used as decimal separator in France,
 * I may also change it to ';'.
 * <br> Here is how I can do that very easily.
 */
public class LocalizedEvaluator extends DoubleEvaluator {
	/** Defines the new function (square root).*/
	private static final Parameters PARAMS;
	
	static {
		// Gets the default DoubleEvaluator's parameters
		PARAMS = DoubleEvaluator.getDefaultParameters();
		// adds the translations
		PARAMS.setTranslation(DoubleEvaluator.SUM, "somme");
		PARAMS.setTranslation(DoubleEvaluator.AVERAGE, "moyenne");
		// Change the default function separator
		PARAMS.setFunctionArgumentSeparator(';');
	}

	private final NumberFormat format;
	private final char effectiveThousandsSeparator;

	public LocalizedEvaluator() {
		super(PARAMS);
		// Create a French number formatter
		format = NumberFormat.getInstance(Locale.FRENCH);
		format.setGroupingUsed(true);
		// Unfortunately, Java treatment of French thousands separator is ... weird:
		// Most French people (like me some years ago) think the separator is a space.
		// But java thought (at least until java 8) it was Non-breaking space. But since
		// (at least Java 17) they changed their mind and now it's "Espace fine insécable".
		// As they have other priorities than the stability of their API, trying to parse
		// with the wrong category of space gives a wrong result.
		// Let's try to be kind with or users: We will replace all kind of spaces by the one java accepts
		effectiveThousandsSeparator = format.format(1000.0).charAt(1);
	}

	@Override
	protected Double toValue(String literal, Object evaluationContext) {
		// Override the method that converts a literal to a number, in order to match with
		// the French decimal separator
		try {
			// For a strange reason, Java thinks that only "fine non breaking spaces" or "non breaking spaces" are
			// French thousands separators. So, we will replace all kind of spaces in the literal by the java one
			literal = literal.replace(' ', effectiveThousandsSeparator); 
			literal = literal.replace((char)0x00A0, effectiveThousandsSeparator); 
			literal = literal.replace((char)0x202F, effectiveThousandsSeparator); 
			return format.parse(literal).doubleValue();
		} catch (ParseException e) {
			// If the number has a wrong format, throw the right exception.
			throw new IllegalArgumentException(literal+" is not a number");
		}
	}
	
	public static void main(String[] args) {
		// Test that all this stuff is ok
		LocalizedEvaluator evaluator = new LocalizedEvaluator();
		String expression = "3 000 +moyenne(3 ; somme(1,5 ; 7 ; -3,5))";
		System.out.println (expression+" = "+evaluator.format.format(evaluator.evaluate(expression)));
	}
}
