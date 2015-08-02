<?php
/* Tutorial (en) */
$srcRoot=substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]));
include $srcRoot."/site/canvas.php";
include_once($srcRoot."/site/geshi/geshi.php");
$page = new Canvas();
$page->generateHeader();
if (isset($_GET['chapter'])) {
	$chapter = $_GET['chapter'];
} else {
	$chapter = "simplest";
}

function displayCodeLink($name) {
	$fileURL = "http://".$_SERVER['SERVER_NAME']."/en/doc/tutorial/".ucfirst($name).".java";
	echo '<a href="'.$fileURL.'">'.$fileURL."</a><br>";
}

function displayCode($name) {
	global $srcRoot;
	$fileName = $srcRoot."/en/doc/tutorial/".ucfirst($name).".java";
	$source = file_get_contents ($fileName);
	$source = str_replace("\t", "  ",$source); // Replace tabs with 2 spaces
	$geshi = new GeSHi($source, 'java5');
	echo $geshi->parse_code();
}
?>
<div id="docContent">
<h1>Tutorial</h1>
Table of contents:<ul>
<li><a href="tutorial.php?chapter=simplest">Using Built-in evaluator</a><ul>
<li><a href="tutorial.php?chapter=simplest">The simplest way</a></li>
<li><a href="tutorial.php?chapter=variables">Working with variables</a></li>
</ul>
</li>
<li><a href="tutorial.php?chapter=restricting">Customizing Built-in evaluator</a><ul>
<li><a href="tutorial.php?chapter=restricting">Restricting an evaluator</a></li>
<li><a href="tutorial.php?chapter=extending">Extending an evaluator</a></li>
<li><a href="tutorial.php?chapter=localizing">Localizing an evaluator</a></li>
</ul>
</li>
<li><a href="tutorial.php?chapter=creatingSimple">Creating your own evaluators</a><ul>
<li><a href="tutorial.php?chapter=creatingSimple">Creating a simple evaluator from scratch</a></li>
<li><a href="tutorial.php?chapter=creatingComplex">Creating a complex evaluator</a></li>
<li><a href="tutorial.php?chapter=textualOperators">Working with textual operators</a></li>
</ul>
</li>
</ul>
<hr/>
<?php include "tutorial/".$chapter.".php"; ?>
</div>
<?php $page->generateFooter();?>
