<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$content = array(
	/*
	 * The referring page for this login request.
	 */
	"referer" => pip_history_get_referer()
	);

pip_render_template( 'login', $content );
