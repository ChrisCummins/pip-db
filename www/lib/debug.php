<?php

if ( isset($_SERVER['DEBUG']) ) {

	/* Display errors */
	ini_set( 'display_errors', 'On' );

	/* Set a high error-reporting level */
	error_reporting( E_ALL | E_STRICT );

}
