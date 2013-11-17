<?php

require_once( $_SERVER['PHP_ROOT'] . 'server.php' );
require_once( $_SERVER['PHP_ROOT'] . 'debug.php' );
require_once( $_SERVER['PHP_ROOT'] . 'error.php' );

/* Don't show errors */
ini_set( 'display_errors', 'Off' );

/* Enable debugging */
if ( pip_debugging() ) {
	error_reporting( E_ALL | E_STRICT );
	pip_error_enable_error_handlers();
}


require_once( $_SERVER['PHP_ROOT'] . 'superglobals.php' );
require_once( $_SERVER['PHP_ROOT'] . 'post.php' );
require_once( $_SERVER['PHP_ROOT'] . 'session.php' );

/* Initialise our session */
session_start();

require_once( $_SERVER['PHP_ROOT'] . 'goto.php' );
require_once( $_SERVER['PHP_ROOT'] . 'history.php' );
require_once( $_SERVER['PHP_ROOT'] . 'template.php' );

require_once( $_SERVER['PHP_ROOT'] . 'accounts.php' );
require_once( $_SERVER['PHP_ROOT'] . 'login.php' );
require_once( $_SERVER['PHP_ROOT'] . 'register.php' );

/* Handle login attempt */
if ( pip_login_attempting_login() )
	pip_login_attempt();
else if ( pip_register_attempting_registration() )
	pip_register_attempt();
