<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/* Set the referring page, if applicable, else redirect home */
if ( isset( $_SERVER['HTTP_REFERER'] ) )
	$referer = $_SERVER['HTTP_REFERER'];
else
	$referer = '/search.php';

$content = array(
	/*
	 * The referring page for this login request.
	 */
	"referer" => $referer
	);

pip_render_template( 'login', $content );
