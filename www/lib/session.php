<?php

/*
 * Returns whether the user is currently logged in or not.
 */
function pip_is_logged_in() {
	return isset( $_SESSION['user'] ) &&
		'' !== $_SESSION['user'];
}

/*
 * If logged in, return the session array. Otherwise, return null.
 */
function pip_get_session() {

	if ( pip_is_logged_in() )
		return array( 'user' => $_SESSION['user'] );
	else
		return null;
}

/*
 * Returns the session (if found) to the given array.
 */
function pip_append_session_to_array( $array ) {
	$session = pip_get_session();

	if ( null !== $session )
		$array['session'] = $session;

	return $array;
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
	$_SESSION['user'] = $username;
}

/*
 * Returns whether we're currently attempting a page login.
 */
function pip_attempting_login() {
	return isset( $_POST['user'] );
}


/* Initialise our session */
session_start();

/* Handle login attempt */
if ( pip_attempting_login() ) {
	$username = $_POST['user'];
	$password = "";

	if ( pip_credentials_are_valid( $username, $password ) )
		pip_login( $username, $password );
}
