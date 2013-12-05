<?php

/*
 * The GOTO statement.
 *
 * Urgh...
 */

/*
 * Load a page URL.
 */
function pip_goto( $url ) {
	header( "location: " . $url );
}
