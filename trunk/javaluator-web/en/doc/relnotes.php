<?php
/* Yapbam relnotes (en) */
include substr($_SERVER["SCRIPT_FILENAME"],0,strlen($_SERVER["SCRIPT_FILENAME"])-strlen($_SERVER["PHP_SELF"]))."/framework/relnotes.php";
$relNotes = new RelNotes();
$relNotes->build("relnotes.txt");
?>
