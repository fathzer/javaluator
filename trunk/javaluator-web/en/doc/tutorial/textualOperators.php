<h2>Creating your own evaluators</h2>
<h3>Working with textual operators</h3>
<p>Javaluator was initially designed to work with operators like +,*,=,!=,...<br/>
Some people had problems when trying to implement textual operators like OR, AND, NOT,... and using them in expressions like "type=PORT AND true". The default behaviour of AbstractEvaluator is to consider the OR in "P<b>OR</b>T" like an operator.<br/>
There's a very simple way to overcome this issue: Override the <b>tokenize</b> method of AbstractEvaluator.<br/>
In this example, you can see that expression's tokens are separated each other by the blank character. The tokenize method can simply been implemented like that:
<pre>@Override
protected Iterator<String> tokenize(String expression) {
	return Arrays.asList(expression.split("\\s")).iterator();
}
</pre></p>
<hr/>
<p>Here is the complete sample code of an evaluator that uses textual operators:</p>
<?php displayCode("TextualOperatorsEvaluator");?>
<p>The output is <pre>
type=PORT -> true
type=NORTH -> false
type=PORT AND true -> true
</pre></p>
