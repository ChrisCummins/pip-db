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
 * Returns wheteher a particular POST variable is set or not.
 */
function pip_post_isset( $var ) {
	return isset( $_POST[$var] );
}

/*
 * Return a particular POST variable if defined, else an empty string.
 */
function pip_post_get( $var ) {
	if ( pip_post_isset( $var ) )
		return $_POST[$var];
	else
		return "";
}
