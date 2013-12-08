<?php

/*
 * $_GET superglobal wrapper.
 *
 * Provides a sane API for dealing with $_GET variables.
 */

/*
 * The names of valid GET variables. All accessing of GET variables should be
 * done indirectly using the pip_{get,set}_get_var() API and using the values
 * found in this class.
 */
abstract class GetVariables {

	/* Query variables */
	const Query = "q";
	const QueryExactPhrase = "q_eq";
	const QueryAnyWord = "q_any";
	const QueryNotWord = "q_ne";
	const QuerySource = "q_s";
	const QueryLocation = "q_l";
	const QueryEC1 = "ec1";
	const QueryEC2 = "ec2";
	const QueryEC3 = "ec3";
	const QueryEC4 = "ec4";
	const QueryPiMin = "pi_l";
	const QueryPiMax = "pi_h";
	const QueryMolecularWeightMin = "mw_l";
	const QueryMolecularWeightMax = "mw_h";
	const QueryExperimentalMethod = "ex";
	const QueryTemperatureMin = "t_l";
	const QueryTemperatureMax = "t_h";

	const Record = "id";
	const StartAt = "start";

	static function val() {
		return array(
			self::Query,
			self::QueryExactPhrase,
			self::QueryAnyWord,
			self::QueryNotWord,
			self::QuerySource,
			self::QueryLocation,
			self::QueryEC1,
			self::QueryEC2,
			self::QueryEC3,
			self::QueryEC4,
			self::QueryPiMin,
			self::QueryPiMax,
			self::QueryMolecularWeightMin,
			self::QueryMolecularWeightMax,
			self::QueryExperimentalMethod,
			self::QueryTemperatureMin,
			self::QueryTemperatureMax,
			self::Record,
			self::StartAt
			);
	}
}

/*
 * Returns whether a given GET variable is valid, i.e. whether it has been
 * defined in the GetVariables class. If the variable name is found, return
 * true, else false.
 */
function pip_get_is_valid( $var ) {
	foreach ( GetVariables::val() as $val ) {
		if ( $val == $var )
			return true;
	}

	return false;
}

/*
 * Ensure that the given variable has an entry in the GetVariables class.
 */
function pip_get_fail_if_not_valid( $var ) {
	if ( !pip_get_is_valid( $var ) ) {
		throw new IllegalSuperglobalException( $var, '_GET',
						       GetVariables::val() );
	}
}

/*
 * Returns wheteher a particular GET variable is set or not.
 */
function pip_get_isset( $var ) {
	pip_get_fail_if_not_valid( $var );

	return isset( $_GET[$var] );
}

/*
 * Return a particular GET variable if defined, else an empty string.
 */
function pip_get( $var ) {
	pip_get_fail_if_not_valid( $var );

	if ( pip_get_isset( $var ) )
		return $_GET[$var];
	else
		return "";
}
