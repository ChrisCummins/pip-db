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

	static function val() {
		return array(
			self::Username,
			self::Password
			);
	}
}

/*
 * Returns whether a given SESSION variable is valid, i.e. whether it has been
 * defined in the SessionVariables class. If the variable name is found, return
 * true, else false.
 */
function pip_session_is_valid( $var ) {
	foreach ( SessionVariables::val() as $val ) {
		if ( $val == $var )
			return true;
	}

	return false;
}

/*
 * Ensure that the given variable has an entry in the SessionVariables class.
 */
function pip_session_fail_if_not_valid( $var ) {
	if ( !pip_session_is_valid( $var ) ) {
		throw new IllegalSuperglobalException( $var, '_SESSION',
						       SessionVariables::val() );
	}
}

/*
 * Returns wheteher a particular session variable is set or not.
 */
function pip_session_isset( $var ) {
	pip_session_fail_if_not_valid( $var );

	return isset( $_SESSION[$var] );
}

/*
 * Return a particular session variable if defined, else an empty string.
 */
function pip_session_get( $var ) {
	pip_session_fail_if_not_valid( $var );

	if ( pip_session_isset( $var ) )
		return $_SESSION[$var];
	else
		return "";
}

/*
 * Sets a particular session variable.
 */
function pip_session_set( $var, $val ) {
	pip_session_fail_if_not_valid( $var );

	$_SESSION[$var] = $val;
}

/*
 * Unset a specific session variable. If no variable is given, unset all
 * variables.
 */
function pip_session_unset( $var = null ) {
	if ( null == $var )
		session_unset();
	else {
		pip_session_fail_if_not_valid( $var );

		session_unset( $var );
	}
}
