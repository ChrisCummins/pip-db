<?php

/*
 * The names of valid POST variables. All accessing of POST variables should be
 * done indirectly using the pip_{get,set}_post_var() API and using the values
 * found in this class.
 */
abstract class PostVariables
{
	const Username = "user";
	const Password = "pass";
	const Action  = "action";

	function val() {
		return array(
			self::Username,
			self::Password,
			self::Action
			);
	}
}

/*
 * Legal values for the POST['action'] variable.
 */
abstract class PostActionValues {
	const Login = "login";
	const Register = "register";
}

/*
 * Returns whether a given POST variable is valid, i.e. whether it has been
 * defined in the PostVariables class. If the variable name is found, return
 * true, else false.
 */
function pip_post_is_valid( $var ) {
	foreach ( PostVariables::val() as $val ) {
		if ( $val == $var )
			return true;
	}

	return false;
}

/*
 * Ensure that the given variable has an entry in the PostVariables class.
 */
function pip_post_fail_if_not_valid( $var ) {
	if ( !pip_post_is_valid( $var ) )
		throw new Exception( 'Illegal _POST variable: "'
				     . $var . '"' );
}

/*
 * Returns wheteher a particular POST variable is set or not.
 */
function pip_post_isset( $var ) {
	pip_post_fail_if_not_valid( $var );

	return isset( $_POST[$var] );
}

/*
 * Return a particular POST variable if defined, else an empty string.
 */
function pip_post_get( $var ) {
	pip_post_fail_if_not_valid( $var );

	if ( pip_post_isset( $var ) )
		return $_POST[$var];
	else
		return "";
}
