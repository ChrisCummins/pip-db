<?php

/*
 * Returns the contents of the _SERVER debugging superglobal.
 */
function pip_server_get_debug() {
	if ( isset( $_SERVER['DEBUG'] ) )
		return $_SERVER['DEBUG'];
	else
		return '';
}

/*
 * Returns wheteher a particular SERVER variable is set or not.
 */
function pip_server_isset( $var ) {
	return isset( $_SERVER[$var] );
}

/*
 * Return a particular SERVER variable if defined, else an empty string.
 */
function pip_server_get( $var ) {
	if ( pip_server_isset( $var ) )
		return $_SERVER[$var];
	else
		return "";
}
