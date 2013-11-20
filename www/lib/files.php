<?php

/*
 * The names of valid FILES variables. All accessing of FILES variables should be
 * done indirectly using the pip_{get,set}_files_var() API and using the values
 * found in this class.
 */
abstract class FilesVariables {
	const Dataset = "f";

	static function val() {
		return array(
			self::Dataset
			);
	}
}

/*
 * Returns whether a given FILES variable is valid, i.e. whether it has been
 * defined in the FilesVariables class. If the variable name is found, return
 * true, else false.
 */
function pip_files_is_valid( $var ) {
	foreach ( FilesVariables::val() as $val ) {
		if ( $val == $var )
			return true;
	}

	return false;
}

/*
 * Ensure that the given variable has an entry in the FilesVariables class.
 */
function pip_files_fail_if_not_valid( $var ) {
	if ( !pip_files_is_valid( $var ) ) {
		throw new IllegalSuperglobalException( $var, '_FILES',
						       FilesVariables::val() );
	}
}

/*
 * Returns wheteher a particular FILES variable is set or not.
 */
function pip_files_isset( $var ) {
	pip_files_fail_if_not_valid( $var );

	return isset( $_FILES[$var] );
}

/*
 * Return a particular FILES variable if defined, else an empty string.
 */
function pip_files( $var ) {
	pip_files_fail_if_not_valid( $var );

	if ( pip_files_isset( $var ) )
		return $_FILES[$var];
	else
		return "";
}
