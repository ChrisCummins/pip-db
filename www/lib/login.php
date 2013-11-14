<?php

/*
 * Legal values for the POST['action'] variable.
 */
abstract class PostActionValues {
	const Login = "login"; /* Attempting to login */
	const Register = "register"; /* Attempting to register a new account */
}

/*
 * Returns whether the user is currently logged in or not.
 */
function pip_login_is_logged_in() {
	return '' !== pip_get_session_var( SessionVariables::Username );
}

/*
 * If logged in, return the user details as an array. Otherwise, return null.
 */
function pip_login_get_user_details() {

	if ( pip_login_is_logged_in() )
		return array(
			'user' => pip_get_session_var( SessionVariables::Username ),
			'pass' => pip_get_session_var( SessionVariables::Password )
			);
	else
		return null;
}

/*
 * Sets the current session.
 */
function pip_login( $username, $password ) {
	pip_set_session_var( SessionVariables::Username, $username );
	pip_set_session_var( SessionVariables::Password, $password );
}

/*
 * Logs out the current user.
 */
function pip_logout() {
	pip_session_unset( SessionVariables::Username );
	pip_session_unset( SessionVariables::Password );
}

/*
 * Returns whether we're currently attempting a page login.
 */
function pip_login_attempting_login() {
	return PostActionValues::Login ==
		pip_post_get( PostVariables::Action );
}

/*
 * Called when a login attempt is unsuccessful.
 */
function pip_login_failed() {
	/*
	 * TODO: Provide visual feedback to the user that the attempt was
	 * unsuccessful.
	 */
}

/*
 * Perform a login attempt.
 */
function pip_login_attempt() {
	$username = pip_post_get( PostVariables::Username );
	$password = pip_post_get( PostVariables::Password );

	if ( pip_accounts_validate_credentials( $username, $password ) )
		pip_login( $username, $password );
	else
		pip_login_failed();
}
