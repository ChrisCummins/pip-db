<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$content = array(
	/*
	 * The referring page for this login request.
	 */
	"referer" => $_SERVER['HTTP_REFERER']
	);

pip_render_template( 'login', $content );
