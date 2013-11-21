<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

function mysql_fetch_all( $resource ) {
	$results = array();

	while ( $r = mysql_fetch_array( $resource ) )
		array_push( $results, $r );

	return $results;
}

$search_text = pip_get( GetVariables::Query );
$resource = pip_db_query( "SELECT record_id, name, source, organ, pi " .
			  "FROM records WHERE name LIKE '%" .
			  pip_string_sanitise( $search_text ) . "%'");

if ( !$resource )
	throw new Exception( 'Failed to query database!' );

$results_count = mysql_num_rows( $resource );
$results = mysql_fetch_all( $resource );

/* URL base */
$base_url = 'http://';
$base_url .= $_SERVER['SERVER_NAME'];
$base_url .= '/s?';
$base_url .= GetVariables::Query . '=' . urlencode( $search_text );

/* Calculate page numbers and whatnot */
$num_of_pages = ceil( $results_count / Pip_Search::ResultsPerPage );
$starting_at = pip_get_isset( GetVariables::StartAt ) ? pip_get( GetVariables::StartAt ) : 0;
$ending_at = $starting_at + Pip_Search::ResultsPerPage;
$current_page = $starting_at / Pip_Search::ResultsPerPage + 1;

/* Generate pagination hrefs */
$pages = array();
for ( $i = 0; $i < $num_of_pages; $i++ ) {
	$url = $base_url;
	if ($i)
		$url .= '&start=' . $i * Pip_search::ResultsPerPage;

	array_push( $pages, $url );
}

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
	"results" => array_slice( $results, $starting_at, $ending_at ),
	/*
	 * The number of results returned.
	 */
	"results_count" => $results_count,
	/*
	 * (optional)
	 * Href to pagination links, if we returned more than one page.
	 */
	"pages" => $pages,
	/*
	 * (optional)
	 * If the results span multiple pages, set this to the page number
	 * currently being displayed.
	 */
	"current_page" => $current_page
	);

pip_render_template( 'results', $content );
