<?php

/*
 * Browser history.
 *
 * Tracks session history.
 */

/*
 * Returns the URL of the referring page. If there is none, then default to the
 * homepage.
 */
function pip_history_get_referer() {
	if ( isset( $_SERVER['HTTP_REFERER'] ) )
		return $_SERVER['HTTP_REFERER'];
	else
		return '/home';
}

/*
 * Returns the current page URL.
 */
function pip_history_get_current() {
	$url = 'http';

	if ( "on" == pip_server_get( "HTTPS" ) )
		$url .= "s";

	$url .= "://" . $_SERVER["SERVER_NAME"];

	if ( "80" != pip_server_get( "SERVER_PORT" ) )
		$url .= ":" .$_SERVER["SERVER_PORT"];

	$url .= $_SERVER["REQUEST_URI"];

	return $url;
}
