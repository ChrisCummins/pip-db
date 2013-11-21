<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

function mysql_fetch_all( $resource ) {
	$results = array();

	while ( $r = mysql_fetch_array( $resource ) )
		array_push( $results, $r );

	return $results;
}

$search_text = pip_get( GetVariables::Query );

$resource = pip_db_query( "SELECT record_id, name, source, organ, pi FROM records " .
			  "WHERE name LIKE '%" .
			  pip_string_sanitise( $search_text ) . "%'");

if ( !$resource )
	throw new Exception( 'Failed to query database!' );

$results_count = mysql_num_rows( $resource );
$results = mysql_fetch_all( $resource );



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
