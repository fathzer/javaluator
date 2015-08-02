<?php
/* Javaluator faq (en) */
include substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]))."/site/canvas.php";
$page = new Canvas();
$page->generateHeader();
?>
<script type="text/javascript" src="http://<?php echo $_SERVER['SERVER_NAME'];?>/site/selectChapters.php"> </script>
<div id="docContent"><h1>Frequently asked questions</h1>
<ul>
  <li><a href="#q1" onclick="select('1')">Is Javaluator free ?</a></li>
  <li><a href="#q3" onclick="select('3')">I have problem using Javaluator, who can help me ?</a></li>
  <li><a href="#q4" onclick="select('4')">How can I submit a bug report or an enhancement request ?</a></li>
  <!--li><a href="#q6" onclick="select('6')">Can I help Javaluator ?</a></li-->
</ul>
<hr style="width: 100%; height: 2px;"/>

<div id="chapter1"><h2><a name="q1"></a>Is Javaluator free ?</h2>
Yes.<br/>
Javaluator is distributed under LGPL v3 license. This means that you are free to download, use, and
distribute Javaluator embeded in your own application.<br/>
If you made some modifications on Javaluator source code, you have to redistribute it under LGPL compatible license.
</div>

<div id="chapter3"><h2><a name="q3"></a>I have a question about JEvaluator, who can help me ?</h2>
The first thing, if it is a question about how to use Javaluator, is to have a look at <a href="javadoc">API documentation</a>.<br/>
If you don't get the answer, or if your question is not technical, please <a href="http://sourceforge.net/p/javaluator/tickets/new/">open a ticket</a>. 
</div>

<div id="chapter4"><h2><a name="q4"></a>How can I submit a bug report or a feature request ?</h2>
<p>You can submit bug reports and feature request by <a href="http://sourceforge.net/p/javaluator/tickets/new/">opening a ticket</a>.
We study every request ... Although we can not satisfy them all.</p>
</div>

<!--div id="chapter6" class="lastChapter"><h2><a name="q6"></a>Can I help Yapbam ?</h2>
Yes, definitly yes.<br/>
All details are given on the <a href="../help">"Help Yapbam !"</a> page.
</div>
<script type="text/javascript"-->
<!--
	selectByURL();
-->
</script>
</div>
<?php $page->generateFooter();?>
