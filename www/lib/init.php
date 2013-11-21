<?php

/*
 * PIP initialisation
 *
 * The purpose of this file is to setup the website controller, exposing
 * functions to communicate with the data backend and an API for rendering the
 * frontend. The PHP file for every page should include this script, as it acts
 * as a gateway to the full website API.
 */

require_once( $_SERVER['PHP_ROOT'] . 'config.php' );

/*********************************************************/
/* Enable debugging, error handling, and server-side API */
/*********************************************************/
require_once( $_SERVER['PHP_ROOT'] . 'server.php' );
require_once( $_SERVER['PHP_ROOT'] . 'debug.php' );
require_once( $_SERVER['PHP_ROOT'] . 'error.php' );

pip_error_disable();

if ( pip_debugging() ) {
	pip_error_enable_strict();
	pip_error_enable_error_handlers();
}


/*************************************************/
/* Provide controlled access to PHP superglobals */
/*************************************************/
require_once( $_SERVER['PHP_ROOT'] . 'superglobals.php' );
require_once( $_SERVER['PHP_ROOT'] . 'get.php' );
require_once( $_SERVER['PHP_ROOT'] . 'post.php' );
require_once( $_SERVER['PHP_ROOT'] . 'files.php' );
require_once( $_SERVER['PHP_ROOT'] . 'session.php' );

pip_session_start();


/******************************************************/
/* Libraries and functions for rendering the frontend */
/******************************************************/
require_once( $_SERVER['PHP_ROOT'] . 'goto.php' );
require_once( $_SERVER['PHP_ROOT'] . 'history.php' );
require_once( $_SERVER['PHP_ROOT'] . 'template.php' );


/***************************************************************/
/* Libraries and functions for communicating with data backend */
/***************************************************************/
require_once( $_SERVER['PHP_ROOT'] . 'crypt.php' );
require_once( $_SERVER['PHP_ROOT'] . 'string.php' );
require_once( $_SERVER['PHP_ROOT'] . 'password.php' );
require_once( $_SERVER['PHP_ROOT'] . 'db.php' );
require_once( $_SERVER['PHP_ROOT'] . 'accounts.php' );

pip_db_init();


/******************************/
/* High level data structures */
/******************************/
require_once( $_SERVER['PHP_ROOT'] . 'record.php' );


/************************************/
/* Site behaviour and interactivity */
/************************************/
require_once( $_SERVER['PHP_ROOT'] . 'login.php' );
require_once( $_SERVER['PHP_ROOT'] . 'register.php' );
require_once( $_SERVER['PHP_ROOT'] . 'upload.php' );


/* Handle login attempt */
if ( pip_login_attempting_login() )
	pip_login_attempt();
else if ( pip_register_attempting_registration() )
	pip_register_attempt();

/* Handle file upload */
if ( pip_upload_new_file() )
	pip_upload_parse_file();
