<?php
/* Home page (en) */
$siteRoot = substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]));
include $siteRoot."/site/canvas.php";
$page = new Canvas();
$page->generateHeader();
include $siteRoot."/site/Mobile_Detect.php";
$detect = new Mobile_Detect();
?>
Javaluator is a simple, but powerful, <a href="http://en.wikipedia.org/wiki/Infix_notation">infix</a>
expression evaluator for Java.<br/>
<br/>
Evaluating an expression is as simple as the following lines :<br/>
<br/>
<code>String expression = "(2^3-1)*sin(pi/4)/ln(pi^2)";<br/>
Double result = new DoubleEvaluator().evaluate(expression);</code>
<br/>
<?php 
	if (!$detect->isMobile()) {
?>
<!--<script src="http://www.java.com/js/deployJava.js" type="text/javascript">
</script><script type="text/javascript">
	if (!deployJava.versionCheck('1.6')) {
		document.write("A live demonstration is available for browsers that support java 1.6+<br/>"); 
		document.write("You need to install java and/or enable applets in your browser<br/><br/>"); 
	} else {
		document.write("<hr/><br/>Try it now !!!<br/><br/>");
		var attributes = { code:'com.fathzer.soft.javaluator.demo.DemoApplet',
			archive:'../../site/demo/JavaluatorDemo<?php include $siteRoot."/site/demo/demoId.txt"?>.jar',
			width:730, height:300} ;
		deployJava.runApplet(attributes, {}, '1.6');
	}
</script><noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>
-->
<applet code = 'com.fathzer.soft.javaluator.demo.DemoApplet' 
        archive = '../../site/demo/JavaluatorDemo<?php include $siteRoot."/site/demo/demoId.txt"?>.jar', 
        width = 730, 
        height = 300>
</applet>
<hr/>
<?php
	}
?>
<h3>Key features:</h3>
<ul>
  <li><b>Functions support</b>: including variable number of arguments and functions with no argument.</li>
  <li><b>Constants support</b>: for example pi and e when evaluating expressions on doubles.</li>
  <li><b>Variables support</b>: Expressions can include variables (example "sin(x)").</li>
  <li><b>Highly configurable</b>:
This library comes with some built-in functions and operators, it is very easy to reduce the number of them (for example, the trigonometric
functions can be removed from the function set if you want to implement a financial calculator).<br/>
It's also easy to define your own bracket pairs ({},[], etc ...) or to add your own operators, functions or constants.</li>
  <li><b>Extensible</b>: This library is provided with an evaluator that deals with expression on real numbers, if you need one on complex numbers, or on anything else, you can implement your own.</li>
  <li><b>Localization</b>: Function and constant's names can be changed easily</li>
  <li><b>Expression syntax check</b>: Basic infix evaluators consider
expressions such as "4 3 +" as valid expressions. Javaluator performs a full syntax
check while evaluating the expression.</li>
  <li><b>Small footprint</b>: This library is distributed as a 26kB jar. It needs no external library in order to run.</li>
</ul>
If you want to learn more on these features, please have a look at the <a href="../doc/tutorial.php"><b>tutorial</b></a>.<br/>
<hr/>
<h3>Limitations:</h3>
<ul>
  <li>Only unary and binary operators are supported (ternary operators such as <i>condition</i><b>?</b><i>value if true</i><b>:</b><i>value if false</i>
  are not).</li>
  <li>Non-associative operators are not supported.</li>
</ul>
<br/>
<hr/>
This library is available for free under <a href="http://opensource.org/licenses/lgpl-3.0.html">LGPL v3 license</a>.<br/><br/>
<?php $page->generateFooter();?>
