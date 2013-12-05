<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * The prototype overview page.
 *
 * Should not be included in production builds.
 */

$template = new Pip_Template( 'demo' );
$template->render();
