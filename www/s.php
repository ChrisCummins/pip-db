<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$search_text = pip_get( GetVariables::Query );

$result = pip_db_query( "SELECT record_id, name, source, organ, pi FROM records " .
			"WHERE name LIKE '%" .
			pip_string_sanitise( $search_text ) . "%'");

if ( !$result )
	throw new Exception( 'Failed to query database!' );

$results = array();

while ( $mysql_row = mysql_fetch_assoc( $result ) ) {

	$row = array();

	foreach ( $mysql_row as $key => $value ) {
		array_push( $row, $value );
	}

	array_push( $results, $row );
}

$results_count = count( $results );

$content = array(
	/*
	 * The search text.
	 */
	"search_text" => $search_text,
	/*
	 * (optional)
	 * Href to download the results.
	 */
	"download" => "http://127.0.0.1",
	/*
	 * An array of results. This can be empty if no results were found.
	 */
	"results" => $results,
	/*
	 * The number of results returned.
	 */
	"results_count" => $results_count,
	);

pip_render_template( 'results', $content );
