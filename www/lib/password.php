<?php

function pip_password_get_hash( $password, $rounds = 7 ) {

	$salt = "";
	$salt_chars = array_merge(range('A','Z'), range('a','z'), range(0,9));

	for($i=0; $i < 22; $i++)
		$salt .= $salt_chars[array_rand($salt_chars)];

	return crypt($password, sprintf('$2a$%02d$', $rounds) . $salt);
}

function pip_password_match( $password, $hash ) {
	return $hash == crypt( $password, $hash );
}
