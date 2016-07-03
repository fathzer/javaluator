<?php header("Content-type: text/javascript"); ?>
<?php
	include "cssConstants.php";
?>
/** This script allows to emphasize the last question clicked.*/
var current=0; // The currently selected question number.
/* Emphasize a question by its number */
function select(id) {
  if (id!=current) {
	chapter = document.getElementById("chapter"+current);
	if (chapter!=null) chapter.style.backgroundColor="transparent";
	chapter = document.getElementById("chapter"+id);
	if (chapter!=null) chapter.style.backgroundColor="<?php echo ($highlight_color)?>";
    current = id;
  }
}

/** Emphasize the question selected in the current URL ... if there's one. */
function selectByURL() {
	parsing=document.URL.split("#");
	if ((parsing.length==2) && (parsing[1].charAt(0)=="q")) {
		anchor = parsing[1];
		number = parseFloat(anchor.substr(1,anchor.length-1));
		if (isNaN(number)) number = 0;
		select (number);
	}
}
