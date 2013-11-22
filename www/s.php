<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

function fetch_all( $resource ) {
	$results = array();

	while ( $r = mysql_fetch_assoc( $resource ) ) {
		$row = array();

		foreach ( $r as $k )
			array_push( $row, $k );

		array_push( $results, $row );
	}

	return $results;
}

function get_results_page_url( $num ) {
	$base_url = 'http://';
	$base_url .= $_SERVER['SERVER_NAME'] . $_SERVER['DOCUMENT_PREFIX'];
	$base_url .= '/s?';
	$base_url .= GetVariables::Query . '=';
	$base_url .= urlencode( pip_get( GetVariables::Query ) );

	$url = $base_url;

	if ( $num > 1 )
		$url .= '&start=' . ( $num - 1 ) * Pip_Search::ResultsPerPage;

	return $url;
}

function get_results_page( $num ) {
	return array(
		"num" => $num,
		"href" => get_results_page_url( $num )
		);
}

$start_time = microtime( true );

$search_text = pip_get( GetVariables::Query );
$resource = pip_db_query( "SELECT record_id, name, source, organ, pi " .
			  "FROM records WHERE name LIKE '%" .
			  pip_string_sanitise( $search_text ) . "%'");

if ( !$resource )
	throw new Exception( 'Failed to query database!' );

$results_count = mysql_num_rows( $resource );
$results = fetch_all( $resource );

/* Calculate page numbers and whatnot */
$num_of_pages = ceil( $results_count / Pip_Search::ResultsPerPage );
$starting_at = pip_get_isset( GetVariables::StartAt ) ? pip_get( GetVariables::StartAt ) : 0;
$ending_at = $starting_at + Pip_Search::ResultsPerPage;
$current_page = $starting_at / Pip_Search::ResultsPerPage + 1;

/* Generate pagination hrefs */
$pages = array();
$starting_page = max( 1, $current_page - Pip_Search::MaxPaginationLinks / 2 );

$ending_page = min( $num_of_pages + 1,
		    $starting_page + Pip_Search::MaxPaginationLinks);

for ( $i = $starting_page; $i < $ending_page; $i++ ) {
	$url = get_results_page_url( $i );

	array_push( $pages, array( "num" => $i, "href" => $url ) );
}

$end_time = microtime( true );

$elapsed_time = $end_time - $start_time;

$content = array(
	/*
	 * The search text.
	 */
	"search_text" => $search_text,
	/*
	 * The elapsed time for the query (in seconds).
	 */
	"elapsed_time" => $elapsed_time,
	/*
	 * (optional)
	 * Href to download the results.
	 */
	"download" => "http://127.0.0.1",
	/*
	 * An array of results. This can be empty if no results were found.
	 */
	"results" => array_slice( $results, $starting_at, Pip_Search::ResultsPerPage ),
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
	"current_page" => get_results_page( $current_page ),
	"first_page" => get_results_page( 1 ),
	"last_page" => get_results_page( $num_of_pages )
	);

$template = new Pip_Template( 'results' );
$template->render( $content );
