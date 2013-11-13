<?php

require_once( 'lib/init.php' );

$content = array(
	/*
	 * (optional)
	 * The configuration for the navbar element. See html/_navbar.html.
	 */
	"_navbar" => array(
		"search_text" => "Alkaline"
		),
	/*
	 * (optional)
	 * The configuration for the title element. See html/_title.html.
	 */
	"_title" => array(
		download => "http://127.0.0.1"
		),
	/*
	 * The names of the protein. Each protien must have at least one name,
	 * which is used to set the page title. Additional names after the first
	 * element will be listed as 'Alternative names'.
	 */
	"names" => array(
		"Alkaline phosphatese",
		"Phosphatese alkaline",
		),
	/*
	 * The record properties. These are in <key> <value> pairs where the key
	 * is the table entry name.
	 */
	"properties" => array(
		"Enzyme Commission number" => "3.2.1.52",
		"Source" => "Human",
		"Location" => "Placenta",
		"pI" => "4.6",
		"Molecular weight" => "116000 - 116000",
		"Sub unit no" => "2",
		"Sub unit MW" => "58000",
		"Number of Iso Enzymes" => "Many",
		"Valid sequences available" => "Yes",
		"Method" => "Isoelectric focusing",
		"Temperature" => "22 C"
		),
	/*
	 * (optional)
	 * The record meta-data.
	 */
	"meta" => array(
		"added" => "on March 10th 2012",
		"edited" => "6 hours ago"
		),
	/*
	 * (optional)
	 * The external links properties. These are in <name, href> pairs and
	 * represent individual external reference buttons.
	 */
	"links" => array(
		"Full Text" => "http://127.0.0.1",
		"PubMed" => "http://127.0.0.1",
		"Species Taxonomy" => "http://127.0.0.1",
		"Protein Sequence" => "http://127.0.0.1"
		),
	/*
	 * (optional)
	 * The "Reference this Page" section.
	 */
	"ref" => array(
		"author" => "pip-db",
		"year" => "2012",
		"title" => "Alkaline phosphatese",
		"site" => "Protein Isoelectric Point Database",
		"href" => "http://www.pip-db.com/r?id=12023"
		)
	);

render_template( 'details', $content );
