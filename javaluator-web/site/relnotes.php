<?php
include_once "canvas.php";
class RelNotes extends Canvas {
	function RelNotes() {
		parent::Canvas();

		// The attributes needed for file processing
		$this->divOpened = false; // Has the version opened a div element (the "not yet released" version didn't open a div element)
		$this->needSeparator = false; // Is a separator needed between current version and next one
		$this->firstOne = true; // Is it the first already released version ?
		$this->changes = array(); // The current version's changes
		$this->fixes = array(); // The current version's bug fixes
		$this->known = array(); // The current version's known bugs
		unset($this->currentList); // The array (changes or fix) we are currently reading
		unset($this->wordings); // The wordings
	}
	
	function build($file) {
		parent::generateHeader();
		echo '<h1>'.$this->translations->relnotesTitle.'</h1>'."\n";
		$fic = fopen($file, 'r');
		if (!$fic) {
			// unable to find the relnotes file
		} else {
			// Read the wordings
			$this->wordings = fgetcsv($fic, 0, "\t");
			while (!feof($fic)) {
				$ligne = fgetcsv($fic, 0, "\t");
				$code = $ligne[0];
				$line = count($ligne)>1 ? $ligne[1] : "";
				if ($code=='version') {
					$this->openVersion($line);
				} else if ($code=='improvement') {
					$this->currentArray = &$this->changes;
				} else if ($code=='fix') {
					$this->currentArray = &$this->fixes;
				} else if ($code=='known') {
					$this->currentArray = &$this->known;
				} else if (($code=='') && ($line!='')) {
					array_push($this->currentArray, $line);
				}
			}
			$this->closeVersion();
			fclose($fic);
		}
		parent::generateFooter();
	}
	
	private function openVersion($version) {
		$this->closeVersion();
		if ($version=='next') {
			// Version "not yet released"
			echo '<h2>'.$this->wordings[0].'</h2>';
			$this->needSeparator = true; // There's always a separator between next version and others
		} else {
			// Other versions
			if ($this->needSeparator) {
				echo '<hr/>'."\n";
			}
			echo '<div class="relnotes-version"><h2>'.$version.'</h2>'."\n";
			$this->divOpened = true;
			$this->needSeparator = $this->firstOne;
			$this->firstOne = false;
		}
	}
	
	private function closeVersion() {
		$this->output($this->known, 'relnotes-knownBugs', $this->wordings[1], $this->wordings[2]);
		$this->output($this->changes, 'relnotes-new', $this->wordings[4], $this->wordings[4]);
		$this->output($this->fixes, 'relnotes-bugFix', $this->wordings[5], $this->wordings[6]);
		$this->changes = array();
		$this->fixes = array();
		$this->known = array();
		if ($this->divOpened) {
			echo '</div>'."\n";
			$this->divOpened = false;
		}
	}
	
	private function output($array, $class, $singular, $plural) {
		if (sizeof($array)!=0) {
			$title = (sizeof($array)==1) ? $singular : $plural;
			echo '<div class="'.$class.'"><h3>'.$title.'</h3><ul>';
			foreach ($array as &$value) {
				echo '<li>'.$value.'</li>'."\n";
			}
			echo '</ul></div>'."\n";
		}
	}
}
?>