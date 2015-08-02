<h2>Customizing built-in evaluator</h2>
<h3>Extending an evaluator</h3>
<p>Javaluator comes with a basic set of common operators, functions, constants and brackets. If you need more, you can add your own.</p>
<p>In this example, we will add a new function: sqrt (square root)</p>
<p>First we have to create the <a href="http:javadoc/net/astesana/javaluator/Function.html">Function</a> instance. This instance describes the name, and argument counts of the function.</p>
<pre>
// The function has one argument and its name is "sqrt"
Function SQRT = new Function("sqrt", 1);
</pre>
<p>When creating the evaluator, we'll have to inform it that there is an extra function.<br/>
This is done by passing a <a href="http:javadoc/net/astesana/javaluator/Parameters.html">parameters</a> argument to the DoubleEvaluator constructor.</p>
<pre>
// Gets the default DoubleEvaluator's parameters
Parameters params = DoubleEvaluator.getDefaultParameters();
// add the new sqrt function to these parameters
params.add(SQRT);
</pre>
<p>We now have to extend the <a href="http:javadoc/net/astesana/javaluator/DoubleEvaluator.html">DoubleEvaluator</a> class in order to implement the new function.<br/>
This is done by overriding its <a href="http://127.0.0.1/en/doc/javadoc/net/astesana/javaluator/DoubleEvaluator.html#evaluate(net.astesana.javaluator.Function, java.util.Iterator, java.lang.Object)">evaluate(Function function, Iterator<Double> arguments, Object evaluationContext)</a> method.
<pre>
// Create a new subclass of DoubleEvaluator that support the new function
DoubleEvaluator evaluator = new DoubleEvaluator(params) {
  @Override
  protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
    if (function == SQRT) {
      // Implements the new function
      return Math.sqrt(arguments.next());
    } else {
      // If it's another function, pass it to DoubleEvaluator
      return super.evaluate(function, arguments, evaluationContext);
    }
  }
};
</pre>
</p>
<p>The same kind of mechanisms apply to adding a new operator or a new constant.</p>
<p>You can add support for new kind of brackets in the expression by adding them to the parameters.</p>
<pre>
// Add square brackets [] to the list of supported brackets in the expressions
params.addExpressionBracket(BracketPair.BRACKETS);
</pre>
<br/><hr/>
<p><br>Here is a complete working sample code:</p>
<?php displayCode("ExtendedDoubleEvaluator");?>
<p>The output is <code>sqrt(abs(-2))^2 = 2.0000000000000004</code></p>
