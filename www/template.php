<?php

$template_dir = './html/';
$twig_autoloader = './lib/Twig/Autoloader.php';

require_once( $twig_autoloader );

Twig_Autoloader::register();

$twig_loader = new Twig_Loader_Filesystem( $template_dir );
$twig_args = array();

$twig = new Twig_Environment( $twig_loader, $twig_args );

function template_exists( $template_name ) {
	global $template_dir;

	return file_exists( $template_dir . $template_name );
}

function render_template( $template_name, $template_args = array() ) {
	global $twig;

	if ( '_' == $template_name[0] ) {
		echo 'template.php: Private template should not be rendered';
		return;
	}

	$template_extension = '.html';
	$template_file = $template_name . $template_extension;

	if ( !template_exists( $template_file ) ) {
		echo 'template.php: Template not found!';
		return;
	}

	$template = $twig->loadTemplate( $template_file );

	$template->display( $template_args );
}
