<?php

require_once( 'lib/functions.php' );

$content = array(
	/* The names of the protein. Each protien must have at least one name,
	 * which is used to set the page title. Additional names after the first
	 * element will be listed as 'Alternative names'. */
	"names" => array(
		"Alkaline phosphatese",
		"Phosphatese alkaline",
		)
	);

render_template( 'details', $content );
