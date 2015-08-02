<h2>Customizing built-in evaluator</h2>
<h3>Restricting an evaluator</h3>
<p>Javaluator come with some built-in functions or operators. But, for some uses, it could be too much.</p>
<p>Imagine you are developping a simple calculator to learn basic operations to children. For sure, the built-in
trigonometric functions are not valuable. The good news is that Javaluator allows you to customize built-in evaluator to recognize only
a subset of the built-in functions, operators, constants, or brackets.</p>
<p>This could be done by passing a <a href="http:javadoc/net/astesana/javaluator/Parameters.html">parameters</a> argument to the <a href="http:javadoc/net/astesana/javaluator/DoubleEvaluator.html#DoubleEvaluator(net.astesana.javaluator.Parameters)">built-in constructor</a>.</p>
<p>First, you have to create a <a href="http:javadoc/net/astesana/javaluator/Parameters.html">Parameters</a> instance, then add the functions, operators, constants and brackets you want to it.
<pre>
Parameters params = new Parameters();
// Define the parameters.
params.add(DoubleEvaluator.PLUS);
...
params.addExpressionBracket(BracketPair.PARENTHESES);
</pre>
<p>Then create the evaluator, it will only know what is defined in its parameters.</p>
<pre>
DoubleEvaluator evaluator = new DoubleEvaluator(params);
</pre>
<br/><hr/>
<p>Here is a complete working sample code:</p>
<?php displayCode($chapter);?>
<p>The output is <code>(3*-4)+2 = -10.0<br/>3^2 is an invalid expression<br/>ln(5) is an invalid expression</code></p>