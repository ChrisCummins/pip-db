<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * The file-not-found error page. Served up as the 404 error document by Apache.
 */

$content = array(
	"error" => array(
		'message' => "Sorry, I couldn't find the page you're after."
		)
	);

$template = new Pip_Template( 'error' );
$template->render( $content );
