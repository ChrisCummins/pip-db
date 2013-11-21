<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$template = new Pip_Template( 'theme' );
$template->render( $content );
