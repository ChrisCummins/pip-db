<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$content = array(
	/*
	 * The referring page for this login request.
	 */
	"referer" => pip_history_get_referer()
	);

$template = new Pip_Template( 'login' );
$template->render( $content );
