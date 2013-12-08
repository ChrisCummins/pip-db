<?php

/*
 * User registration.
 *
 * Provides an API for allowing users to register an account at pip-db.
 */

/*
 * Returns whether the user is currently attempting to register a new account.
 */
function pip_register_attempting_registration() {
	return PostActionValues::Register ==
		pip_post_get( PostVariables::Action );
}

/*
 * Called when a registration attempt is unsuccessful.
 */
function pip_register_failed() {
	/*
	 * TODO: Provide visual feedback to the user that the attempt was
	 * unsuccessful.
	 */
}

/*
 * Attempt to register a new user.
 */
function pip_register_attempt() {
	$username = pip_string_sanitise( pip_post_get( PostVariables::Username ) );
	$password = pip_string_sanitise( pip_post_get( PostVariables::Password ) );

	if ( !pip_accounts_account_exists( $username ) ) {
		pip_accounts_add_new( $username, $password );
		pip_login( $username, $password );
	} else
		pip_register_failed();

}
