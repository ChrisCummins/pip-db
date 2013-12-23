<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * The admin page.
 *
 */
$content = array( "build_status" => $_BUILD_SUMMARY );

$template = new Pip_Template( 'admin' );
$template->render( $content );
