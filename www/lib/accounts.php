<?php

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
 * Registers a new account.
 */
function pip_add_account( $username, $password ) {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that whatever details were provided were successful.
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
