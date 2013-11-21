<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$template = new Pip_Template( '404' );
$template->render( );
