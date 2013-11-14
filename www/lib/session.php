<?php

/*
 * The names of SessionVariables. All accessing of sesion variables should be
 * done indirectly using the pip_{get,set}_session_var() API and using the
 * values found in this class.
 */
abstract class SessionVariables
{
	const Username = "user";
	const Password = "pass";
}

/*
 * Returns wheteher a particular session variable is set or not.
 */
function pip_session_var_isset( $var ) {
	return isset( $_SESSION[$var] );
}

/*
 * Return a particular session variable if defined, else an empty string.
 */
function pip_get_session_var( $var ) {
	if ( pip_session_var_isset( $var ) )
		return $_SESSION[$var];
	else
		return "";
}

/*
 * Sets a particular session variable.
 */
function pip_set_session_var( $var, $val ) {
	$_SESSION[$var] = $val;
}

/* Initialise our session */
session_start();
