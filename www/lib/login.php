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
 * Validates whether a set of user credentials are valid.
 */
function pip_credentials_are_valid( $username, $password ) {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that whatever details were provided were successful.
	 */
	return true;
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
 * Registers a new account.
 */
function pip_add_account( $username, $password ) {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that whatever details were provided were successful.
	 */
}

/*
 * Returns whether we're currently attempting a page login.
 */
function pip_attempting_login() {
	return PostActionValues::Login ==
		pip_get_post_var( PostVariables::Action );
}

/*
 * Returns whether the user is currently attempting to register a new account.
 */
function pip_attempting_register() {
	return PostActionValues::Register ==
		pip_get_post_var( PostVariables::Action );
}

/*
 * Called when a login attempt is unsuccessful.
 */
function pip_failed_login() {
	/*
	 * TODO: Provide visual feedback to the user that the attempt was
	 * unsuccessful.
	 */
}

/*
 * Perform a login attempt.
 */
function pip_do_login_attempt() {
	$username = pip_get_post_var( PostVariables::Username );
	$password = pip_get_post_var( PostVariables::Password );

	if ( pip_credentials_are_valid( $username, $password ) )
		pip_login( $username, $password );
	else
		pip_failed_login();
}

/*
 * Called when a registration attempt is unsuccessful.
 */
function pip_failed_register() {
	/*
	 * TODO: Provide visual feedback to the user that the attempt was
	 * unsuccessful.
	 */
}

/*
 * Returns whether a specific user name exists.
 */
function pip_user_exists( $username ) {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that no users exist.
	 */
	return false;
}

/*
 * Attempt to register a new user.
 */
function pip_do_register_attempt() {
	$username = pip_get_post_var( PostVariables::Username );
	$password = pip_get_post_var( PostVariables::Password );

	if ( !pip_user_exists( $username ) ) {
		pip_add_account( $username, $password );
		pip_login( $username, $password );
	} else
		pip_failed_register();

}
