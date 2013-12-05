<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * The data upload page. Should only be accessed by a logged-in user.
 */

$template = new Pip_Template( 'upload' );
$template->render();
