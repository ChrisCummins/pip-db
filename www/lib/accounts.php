<?php

/*
 * Accounts management.
 *
 * Provides an API for creating and validating account credentials.
 */

/*
 * Check that a given password is correct.
 */
function pip_accounts_password_is_correct( $username, $password ) {
	$hash = pip_db_fetch_row( "SELECT pass FROM users where email='$username'" );
	$hash = $hash[0];

	return pip_password_match( $password, $hash );
}

/*
 * Validates whether a set of user credentials are valid.
 */
function pip_accounts_validate_credentials( $username, $password ) {
	return pip_accounts_account_exists( $username ) &&
		pip_accounts_password_is_correct( $username, $password );
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
