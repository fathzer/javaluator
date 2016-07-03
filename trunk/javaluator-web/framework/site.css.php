<?php header("Content-type: text/css"); ?>
<?php
	include "cssConstants.php";
	if (array_key_exists('contentWidth', $_GET)) {
		$content_width = $_GET['contentWidth'];
	}
	header("Content-type: text/css");
?>
#page{
	margin: 0.5em;
}
html,body{
	font-family: Lucida Grande, Verdana, Arial, sans-serif;
	font-size: 12px;
	background-image: url("customization/body.gif");
	margin: 0;
	padding:0;
}
pre, code {
	background: <?php echo $background_color?>;
	text-shadow: 1px 1px #fff;
	border: 1px solid <?php echo $border_color?>;
	display: block;
	padding: 5px;
	margin: 5px 0;
	border-radius: 7px;
	-moz-tab-size: 2;
	-o-tab-size:   2;
	tab-size:      2;
}
#top {
	margin:0 auto;
	margin-bottom: 10px;
	width: <?php echo ($google_width + $content_width + 55)?>px;
}
#flattr {
	float: right;
}
#top img {
	border: 0;
}

#menuBar {
	padding:0px;
	margin-top: 0px;
	margin-bottom: 0px;
	margin-left: auto;
	margin-right: auto;
	width: <?php echo ($google_width + $content_width + 55)?>px;
	background-color: #333;
	border-radius: 7px;
}
#localizedLink {
	margin-right: 0.5em;
	margin-top: 0.4em;
	text-align: right;
	float: right;
}
#localizedLink img {
	margin-left: 0.8em;
	border: 0;
}

#container {
	margin:0 auto;
	margin-top: 10px;
	width: <?php echo ($google_width + $content_width + 55)?>px;
/*	border-style: solid;
	border-width: thin;
	border-color: <?php echo $border_color?>;*/
	border-radius: 7px;
	background-color: <?php echo $main_background_color;?>;
}
#content {
	float: left;
	margin-top: 5px;
	padding-left: 5px;
	width: <?php echo ($content_width + 30)?>px;
}
#content a:link, #content a:visited {
	color: #2a6;
	text-decoration: none;
	padding: 1px;
}
#content a:hover {
	color: #222;
	background-color: #8D8;
	padding: 1px;
}
#advertising {
	float: right; 
	width: <?php echo ($google_width+5)?>px;
	height: 100%;
	margin-right: 0px;
	margin-top: 5px;
	margin-bottom: 10px;
	padding-left: 5px;
	padding-bottom: 5px;
}
#advertising .altAdvertising {
	margin: 5px;
	padding: 5px;
	border-style: solid;
	border-width: 1px;
	border-color: <?php echo $border_color?>;
	border-radius: 5px;
	background-color: <?php echo $background_color?>;
}
#donate {
	text-align: center;
}
.pubTitle {
	color: <?php echo $cool_text_color?>;
	text-align: center;	
	font-size: 100%;
	white-space:nowrap;
}
#footer {
	margin:0 auto;
	margin-top: 10px;
	width: <?php echo ($google_width + $content_width + 55)?>px;
/*	border-style: solid;
	border-width: thin;
	border-color: <?php echo $border_color?>;*/
	background-color: <?php echo $main_background_color;?>;
	border-radius: 7px;
	height:31px;
	font-size: 75%;
}
#ILike {
	margin-left: 5px;
	margin-top: 5px;
	display: inline;
	float: left;
}
.fb-like {
	margin-top: 5px;
	display: inline;
	float: left;
}
.hosted {
	display: inline;
    float: right;
    margin-right:5px;
	margin-top: 5px;
	text-align: right;
}
.SFLogo {
	position: relative;
	top: 5px;
}
.SFLogo img {
	border: 0;
}

/* Back to top button */
#back-top {
	position: fixed;
	bottom: 5px;
	right: 5px;
/*	margin-left: <?php echo ($google_width + $content_width + 60);?>px;*/
}

#back-top a {
/*	width: 108px;*/
	display: block;
	text-align: center;
	font: 11px/100% Arial, Helvetica, sans-serif;
	text-transform: uppercase;
	text-decoration: none;
	color: #000;

	/* transition */
	-webkit-transition: 1s;
	-moz-transition: 1s;
	transition: 1s;
}
#back-top a:hover {
	color: #FFF;
}

/* arrow icon (span tag) */
#back-top span {
	display: block;
	width: 80px;
	height: 40px;
	margin-bottom: 7px;
	background: url(goup.png) no-repeat center center;

	/* transition */
	-webkit-transition: 1s;
	-moz-transition: 1s;
	transition: 1s;
}

/* specific to documentation/relnotes section */
.relnotes-version {
	display: block;
	border: 1px solid #bbbbbb;
	background: #ffffff;
	border-radius: 7px;
	margin-bottom: 10px;
/*	margin-right: 5px;*/
	padding-left: 5px;
}

.relnotes-bugFix {
	display: block;
	border: 1px solid #bbbbbb;
	border-radius: 5px;
	background: #f8fff8;
	color: 099;
	padding-left: 10px;
	margin-bottom: 5px;
	margin-right: 5px;
	font-size: 0.9em;
}
.relnotes-bugFix  h3 {
	font-size: 1.1em !important;
}

/* Last faq chapter */
.lastChapter {
	margin-bottom: 2em;
}

