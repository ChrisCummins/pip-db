<?php

require_once( $_SERVER['PHP_ROOT'] . 'Twig/Autoloader.php' );

Twig_Autoloader::register();


/*
 * For a given template name, return the template source file.
 */
function pip_get_template_file( $name ) {
	return $name . '.html';
}

/*
 * Returns the path to a template file in the form:
 *
 *      <dir>/<name>.<ext>
 */
function pip_get_template_path( $name ) {
	return $_SERVER['HTML_ROOT'] . pip_get_template_file( $name );
}

/*
 * Returns whether a template has a corresponding source file.
 */
function pip_template_exists( $name ) {
	return file_exists( pip_get_template_path( $name ) );
}

/*
 * Returns true if a template is private, else false.
 */
function pip_template_is_private( $name ) {
	return '_' == $name[0];
}

/*
 * Returns the template engine.
 */
function pip_template_engine_get_default() {
	$loader = new Twig_Loader_Filesystem( $_SERVER['HTML_ROOT'] );

	return new Twig_Environment( $loader, array() );
}

/*
 * Returns a given template. If no template engine is supplied, a default engine
 * is used.
 */
function pip_template_get_template( $name, $engine = null ) {
	if ( null == $engine )
		$engine = pip_template_engine_get_default();

	return $engine->loadTemplate( pip_get_template_file( $name ) );
}

/*
 * Throw a custom exception for the tempalte engine.
 */
function _throw_template_error( $msg ) {
	throw new Exception( 'lib/template.php: ' . $msg );
}

/*
 * Renders a given template.
 */
function pip_render_template( $name, $content = array() ) {
	if ( !pip_template_exists( $name ) )
		_throw_template_error( 'Template not found!' );

	if ( pip_template_is_private( $name ) )
		_throw_template_error( 'Private template should not be rendered' );

	/* Add user session to content */
	$session = pip_login_get_user_details();
	if ( null !== $session )
		$content['session'] = $session;

	$template = pip_template_get_template( $name );
	$template->display( $content );
}
