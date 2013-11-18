<?php

function php_string_sanitise( $string ) {

	$string = strip_tags( $string );
	$string = htmlentities( $string );
	$string = stripslashes( $string );
	$string = mysql_real_escape_string( $string );

	return $string;
}
