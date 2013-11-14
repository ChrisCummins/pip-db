<?php

/*
 * Returns whether the user is currently attempting to register a new account.
 */
function pip_login_attempting_register() {
	return PostActionValues::Register ==
		pip_get_post_var( PostVariables::Action );
}

/*
 * Called when a registration attempt is unsuccessful.
 */
function pip_login_registration_failed() {
	/*
	 * TODO: Provide visual feedback to the user that the attempt was
	 * unsuccessful.
	 */
}

/*
 * Attempt to register a new user.
 */
function pip_login_registration_attempt() {
	$username = pip_get_post_var( PostVariables::Username );
	$password = pip_get_post_var( PostVariables::Password );

	if ( !pip_accounts_account_exists( $username ) ) {
		pip_accounts_add_new( $username, $password );
		pip_login( $username, $password );
	} else
		pip_login_registration_failed();

}
