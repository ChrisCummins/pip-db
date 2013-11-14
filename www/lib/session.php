<?php

function get_session() {

	if ( isset( $_SESSION['user'] ) &&
	     '' !== $_SESSION['user'] ) {
		return array( 'user' => $_SESSION['user'] );
	}

	return null;
}

function set_session( $user ) {
	$_SESSION['user'] = $user;
}

function attempt_login() {
	/*
	 * TODO: Actually implement a proper user backend. For now, we just
	 * assume that whatever details were provided were successful.
	 */
	set_session( $_POST['user'] );
}

/* Initialise our session */
session_start();

if (isset($_POST['user'])) {
	attempt_login();
}
