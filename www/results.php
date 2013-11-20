<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$query = pip_get( GetVariables::Query );

$result = pip_db_query( "SELECT name, source, organ, pi FROM records " .
			"WHERE name LIKE '%" .
			pip_string_sanitise( $query ) . "%'");

if ( !$result )
	throw new Exception( 'Failed to query database!' );

$records = array();

while ( $mysql_row = mysql_fetch_assoc( $result ) ) {

	$row = array();

	array_push( $row, "details.php" );

	foreach ( $mysql_row as $key => $value ) {
		array_push( $row, $value );
	}

	array_push( $records, $row );
}

$content = array(
	/*
	 * The search text.
	 */
	"search_text" => $query,
	/*
	 * (optional)
	 * Href to download the results.
	 */
	"download" => "http://127.0.0.1"
	);

if ( $count = count( $records ) ) {
	$content['results'] = $records;
	$content['results_count'] = $count;
}

pip_render_template( 'results', $content );
