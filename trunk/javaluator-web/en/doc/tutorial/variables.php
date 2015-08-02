<h2>Using built-in evaluator</h2>
<h3>Working with variables</h3>
<p>A very common need is to evaluate expressions that contains variables.<br/>
Imagine, for example, that you want to plot the function sin(x) between 0 and pi/2.</p>
<p>First, create a standard real expressions evaluator.
<code>DoubleEvaluator eval = new DoubleEvaluator();</code></p>
<p>Then declare the expression you want to evaluate
<code>String expression = "sin(x)";</code></p>
<p>Now, you will have to evaluate that expression in a loop and increasing the x value at each step.<br/>
The <a href="http:javadoc/net/astesana/javaluator/AbstractEvaluator.html#evaluate(java.lang.String, java.lang.Object)">
AbstractEvaluator.evaluate</a> method allows you to provide an evaluation context to the evaluator.<br/>
We will use an evaluation context that implements <a href="http:javadoc/net/astesana/javaluator/AbstractVariableSet.html">
AbstractVariableSet</a> in order to pass the values of x to the evaluator.<br/>
Javaluator provides you with a concrete implementation which is sufficient in most cases: The <a href="http:javadoc/net/astesana/javaluator/StaticVariableSet.html">
StaticVariableSet</a>.<br/>
Simply create such a variable set with its default construtor.
<code>StaticVariableSet&lt;Double&gt; variables = new StaticVariableSet&lt;Double&gt;()</code></p>
<p>In each loop, it then remains to declare the new value for x and evaluate the expression</p>
<pre>double x = 0;
double step = Math.PI/8;
while (x<=Math.PI/2) {
  // Set the value of x
  <b>variables.set("x", x);</b>
  // Evaluate the expression
  <b>Double result = eval.evaluate(expression, variables);</b>
  // Ouput the result
  ...
  x += step;
}</pre>
<br/><hr/>
<p>Here is the complete sample code:</p>
<?php displayCode($chapter);?>
<p>The output is <pre>
x=0.0 -> sin(x) = 0.0
x=0.39269908169872414 -&gt; sin(x) = 0.3826834323650898
x=0.7853981633974483 -&gt; sin(x) = 0.7071067811865475
x=1.1780972450961724 -&gt; sin(x) = 0.9238795325112867
x=1.5707963267948966 -&gt; sin(x) = 1.0
</pre></p>
