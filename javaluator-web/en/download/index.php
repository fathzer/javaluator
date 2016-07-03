﻿<?php
/* Download Javaluator (en) */
$siteRoot = substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]));
include_once $siteRoot."/framework/canvas.php";

class Download extends Canvas {
	function Download() {
		parent::Canvas();
	}

	function generateExtraHeader() {
		global $siteRoot;
		include $siteRoot."/framework/customization/cssConstants.php";
		?>
		<style type="text/css">
			h1 {
				color: <?php echo $cool_text_color?>;
			}
		</style>
<?php
	}
}
$page = new Download();
$page->generateHeader();?>
<h1>Where could you download Javaluator?</h1>
<a href="http://sourceforge.net/projects/javaluator/files/latest"><img src="../../framework/download.png"/>Here !</a>
<p>Javaluator is also available through Maven:<br><code>
&lt;dependency&gt;<br>
&nbsp;&nbsp;&lt;groupId&gt;com.fathzer&lt;/groupId&gt;<br>
&nbsp;&nbsp;&lt;artifactId&gt;javaluator&lt;/artifactId&gt;<br>
<?php
$query="g%3A%22com.fathzer%22%20AND%20a%3A%22javaluator%22";
$url="https://search.maven.org/solrsearch/select?q=".$query."&rows=1&wt=json";
$json = file_get_contents($url);
if (!$json) {
	$latestVersion = '<a href="http://search.maven.org/#search|gav|1|'.$query.'">see maven central...</a>';
} else {
	$jsonObj=json_decode($json);
	$latestVersion = $jsonObj->{'response'}->{'docs'}[0]->{'latestVersion'};
}
?>
&nbsp;&nbsp;&lt;version&gt;<span class="latestVersion"><?php echo $latestVersion?></span>&lt;/version&gt;<br>
&lt;/dependency&gt;
</code></p>
<?php
$page->generateFooter();?>