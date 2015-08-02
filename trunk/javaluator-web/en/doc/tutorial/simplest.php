<h2>Using built-in evaluator</h2>
<h3>The simplest way</h3>
<p>Javaluator comes with a built-in expression parser that is able to parse expression on real numbers.
<br/>You simply need to instanciate it (call DoubleEvaluator default constructor) and use it (call the evaluate method).</p>
<p>Here is a sample code:</p>
<?php displayCode($chapter);?>
<p>The output is <code>(2^3-1)*sin(pi/4)/ln(pi^2) = 2.1619718020347976</code></p>
