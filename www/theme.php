<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * A page to demonstrate the Bootstrap theme.
 *
 * Should not be included in production builds.
 */

$template = new Pip_Template( 'theme' );
$template->render();
