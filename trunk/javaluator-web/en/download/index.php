<?php
/* Download Javaluator (en) */
include substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]))."/site/canvas.php";
include substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]))."/site/cssConstants.php";
$page = new Canvas();
$page->openHead();?>
<style type="text/css">
	h1 {
		color: <?php echo $cool_text_color?>;
	}
	.note {
		text-color: <?php echo $cool_text_color?>;
		font-size: 75%;
		font-style: italic;
	}
</style>
<?php $page->closeHeadOpenContent();?>
<h1>Where could you download Javaluator ? </h1>
<a href="http://sourceforge.net/projects/javaluator/files/latest"/><img src="../../site/download.png"/>Here !</a>
<p>Javaluator is also available through Maven:</br>
Group id: com.fathzer</br>
Artifact id: javaluator</p>
<?php $page->generateFooter();?>
