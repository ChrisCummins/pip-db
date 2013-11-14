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
function pip_session_isset( $var ) {
	return isset( $_SESSION[$var] );
}

/*
 * Return a particular session variable if defined, else an empty string.
 */
function pip_session_get( $var ) {
	if ( pip_session_isset( $var ) )
		return $_SESSION[$var];
	else
		return "";
}

/*
 * Sets a particular session variable.
 */
function pip_session_set( $var, $val ) {
	$_SESSION[$var] = $val;
}

/*
 * Unset a specific session variable. If no variable is given, unset all
 * variables.
 */
function pip_session_unset( $var = null ) {
	if ( null == $var )
		session_unset();
	else
		session_unset( $var );
}
