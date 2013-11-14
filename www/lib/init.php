<?php

require_once( $_SERVER['PHP_ROOT'] . 'post.php' );
require_once( $_SERVER['PHP_ROOT'] . 'session.php' );

require_once( $_SERVER['PHP_ROOT'] . 'goto.php' );
require_once( $_SERVER['PHP_ROOT'] . 'history.php' );
require_once( $_SERVER['PHP_ROOT'] . 'template.php' );

require_once( $_SERVER['PHP_ROOT'] . 'accounts.php' );
require_once( $_SERVER['PHP_ROOT'] . 'login.php' );


/* Initialise our session */
session_start();

/* Handle login attempt */
if ( pip_login_attempting_login() )
	pip_do_login_attempt();
else if ( pip_login_attempting_register() )
	pip_do_register_attempt();
