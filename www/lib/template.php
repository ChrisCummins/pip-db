<?php

$template_dir = './html/';
$twig_autoloader = './lib/Twig/Autoloader.php';

require_once( $twig_autoloader );

Twig_Autoloader::register();

$twig_loader = new Twig_Loader_Filesystem( $template_dir );
$twig_args = array();

$twig = new Twig_Environment( $twig_loader, $twig_args );

/*
 * Returns whether a template has a corresponding source file.
 */
function pip_template_exists( $template_name ) {
	global $template_dir;

	return file_exists( $template_dir . $template_name );
}

/*
 * Returns true if a template is private, else false.
 */
function pip_template_is_private( $name ) {
	return '_' == $name[0];
}

/*
 * Renders a given template.
 */
function pip_render_template( $template_name, $template_args = array() ) {
	global $twig;

	$template_extension = '.html';
	$template_file = $template_name . $template_extension;

	if ( !pip_template_exists( $template_file ) ) {
		echo 'lib/template.php: Template not found!';
		return;
	}

	if ( pip_template_is_private( $template_name ) ) {
		echo 'lib/template.php: Private template should not be rendered';
		return;
	}

	$session = pip_get_session();

	if ( null !== $session )
		$template_args['session'] = $session;

	$template = $twig->loadTemplate( $template_file );

	$template->display( $template_args );
}
