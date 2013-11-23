<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$content = array(
	"error" => array(
		'message' => "Sorry, I couldn't find the page you're after."
		)
	);

$template = new Pip_Template( '404' );
$template->render( $content );
