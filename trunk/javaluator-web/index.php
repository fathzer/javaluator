<?php
if (!empty($_SERVER['HTTPS']) && ('on' == $_SERVER['HTTPS'])) {
	$uri = 'https://';
} else {
	$uri = 'http://';
}
$uri .= $_SERVER['HTTP_HOST'];

$lang = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
if ($lang != "en") $lang = "en";
header('Location: '.$uri.'/'.$lang.'/home/');
?>
