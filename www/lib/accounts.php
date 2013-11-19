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

	$hash = pip_password_get_hash( $password );

	$result = pip_db_query( "INSERT INTO " .
				"users (email, pass, user_type_id) " .
				"VALUES ('$username', '$hash', 1)" );

	if ( !$result )
		throw new Exception('Failed to create new account!');
}

/*
 * Returns whether a specific user name exists.
 */
function pip_accounts_account_exists( $username ) {

	if ( pip_db_num_rows( "SELECT * FROM users WHERE email='$username'" ) )
		return true;
	else
		return false;
}
