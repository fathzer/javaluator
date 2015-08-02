<h2>Customizing built-in evaluator</h2>
<h3>Localizing an evaluator</h3>
<p>One can think a mathematical expression doesn't depend on the country where it is written.<br/>
It's a mistake.<br/>
<br>The most obvious is that some functions are better understood if they are translated.<br/>
For example, <i><b>somme</b></i> is better that <i><b>sum</b></i> for French people.</p>
<p>Translating the functions or constants names can be done by customizing the evaluator's parameters</p>
<pre>
// Gets the default DoubleEvaluator's parameters
Parameters params = DoubleEvaluator.getDefaultParameters();
// adds the translations
params.setTranslation(DoubleEvaluator.SUM, "somme");
</pre>
<p>Unfortunatly, this is not enough.<br/>
The numbers are not written the same way in all countries. For example, in France, the decimal separator is not a point, but a comma.<br/>
This cause another problem: The comma is the default separator for function arguments. In France, we have to replace this separator by another.<br/>
Once again, for this last problem, the <a href="http:javadoc/net/astesana/javaluator/Parameters.html">Parameters</a> instance will give us the solution.</p>
<pre>
// Change the default function separator
params.setFunctionArgumentSeparator(';');
</pre>
<p>So, it remains the problem of parsing the numbers expressed in French format.<br/>
Fortunately, the evaluator's method that transforms a literal to a number can be overriden.<br/>
Then we will use a subclass of DoubleEvaluator with a specialized conversion from String to Double.</p>
<pre>
// Create a new DoubleEvaluator that supports the French decimal separator
DoubleEvaluator evaluator = new DoubleEvaluator(params) {
  private final DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance(Locale.FRENCH);
  @Override
  protected Double toValue(String literal, Object evaluationContext) {
    // Override the method that converts a literal to a number, in order to match with
    // the French decimal separator
    try {
      return format.parse(literal).doubleValue();
    } catch (ParseException e) {
      // If the number has a wrong format, throw the right exception.
      throw new IllegalArgumentException(literal+" is not a number");
    }
  }
};
</pre>
<p>Maybe you're wondering why not to simply add a Locale to the parameters ?.<br/>
Unfortunately, parsing the numbers is a little bit more complicated than what is done above.<br/>
You have to deal with grouping separators and with strange (ugly) things with French thousands
separator. This requires ... coding :-(<br/>This stuff is implemented below in the complete working code.
<br/><hr/>
<p>Here is a complete working sample code:</p>
<?php displayCode("LocalizedEvaluator");?>
<p>The output is <code>3 000 +moyenne(3 ; somme(1,5 ; 7 ; -3,5)) = 3&nbsp;004</code></p>
