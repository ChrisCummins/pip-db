<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$id = pip_get( GetVariables::Record );
$record = new Pip_Record( $id );

$content = array(
	/*
	 * (optional)
	 * Search box text.
	 */
	"search_text" => pip_get( GetVariables::Query ),
	/*
	 * (optional)
	 * The href to download page.
	 */
	 "download" => "http://127.0.0.1",
	/*
	 * The names of the protein. Each protien must have at least one name,
	 * which is used to set the page title. Additional names after the first
	 * element will be listed as 'Alternative names'.
	 */
	 "names" => $record->get_names(),
	/*
	 * The record properties. These are in <key> <value> pairs where the key
	 * is the table entry name.
	 */
	 "properties" => $record->get_properties(),
	/*
	 * (optional)
	 * The record meta-data.
	 */
	 "meta" => $record->get_meta(),
	/*
	 * (optional)
	 * The external links properties. These are in <name, href> pairs and
	 * represent individual external reference buttons.
	 */
	 "links" => $record->get_links(),
	/*
	 * (optional)
	 * The "Reference this Page" section.
	 */
	 "ref" => $record->get_ref()
	);

pip_render_template( 'details', $content );
