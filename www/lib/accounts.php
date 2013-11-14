<?php

/*
 * Validates whether a set of user credentials are valid.
 */
function pip_accounts_validate_credentials( $username, $password ) {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that whatever details were provided were successful.
	 */
	return true;
}

/*
 * Registers a new account.
 */
function pip_accounts_add_new( $username, $password ) {
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
