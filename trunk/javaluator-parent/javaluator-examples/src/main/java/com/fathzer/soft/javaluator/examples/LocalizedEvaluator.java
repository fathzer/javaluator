package com.fathzer.soft.javaluator.examples;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Parameters;

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

	private DecimalFormat format;

	public LocalizedEvaluator() {
		super(PARAMS);
		// Create a French number formatter
		format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRENCH);
		format.setGroupingUsed(true);
	}

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
}
