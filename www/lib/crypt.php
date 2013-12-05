<?php

/*
 * Cryptography.
 *
 * Provides an API for generating salting hashes.
 */

function pip_crypt( $string, $rounds = 10 ) {

	$salt = "";
	$salt_chars = array_merge( range( 'A', 'Z' ), range( 'a', 'z' ), range( 0,9 ) );

	for( $i=0; $i < 22; $i++ )
		$salt .= $salt_chars[array_rand( $salt_chars )];

	return crypt( $string, sprintf( '$2a$%02d$', $rounds ) . $salt );
}
