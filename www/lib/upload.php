<?php

/*
 * Returns whether we're currently attempting to upload a new file.
 */
function pip_upload_new_file() {
	return PostActionValues::Upload == pip_post_get( PostVariables::Action )
		&& pip_files_isset( FilesVariables::Dataset );
}

function pip_upload_parse_file() {
	$super = pip_files( FilesVariables::Dataset );
	$path = $super['tmp_name'];
	$handle = fopen( $path, 'r' );

	if ( $handle )
		pip_upload_parse_csv( $handle );
	else
		throw new Exception( 'Failed to open submitted file!' );
}

function pip_upload_parse_csv( $handle ) {

	while ( false !== ($line = fgets( $handle ) ) ) {
		$row = str_getcsv( $line, '	' );

		$result = pip_db_query( "INSERT INTO records ".
					"(dataset, ec, name, alt_name, source, organ, " .
					"mw, sub_no, sub_mw, no_iso, pi_max, pi_range_min, " .
					"pi_range_max, pi_major, pi, temp, method, valid, " .
					"sequence, species, citations, abstract, notes) " .
					"VALUES (" .
					"'" . pip_string_sanitise( $row[0] ) . "', " .
					"'" . pip_string_sanitise( $row[1] ) . "', " .
					"'" . pip_string_sanitise( $row[2] ) . "', " .
					"'" . pip_string_sanitise( $row[3] ) . "', " .
					"'" . pip_string_sanitise( $row[4] ) . "', " .
					"'" . pip_string_sanitise( $row[5] ) . "', " .
					"'" . pip_string_sanitise( $row[6] ) . "', " .
					"'" . pip_string_sanitise( $row[7] ) . "', " .
					"'" . pip_string_sanitise( $row[8] ) . "', " .
					"'" . pip_string_sanitise( $row[9] ) . "', " .
					"'" . pip_string_sanitise( $row[10] ) . "', " .
					"'" . pip_string_sanitise( $row[11] ) . "', " .
					"'" . pip_string_sanitise( $row[12] ) . "', " .
					"'" . pip_string_sanitise( $row[13] ) . "', " .
					"'" . pip_string_sanitise( $row[14] ) . "', " .
					"'" . pip_string_sanitise( $row[15] ) . "', " .
					"'" . pip_string_sanitise( $row[16] ) . "', " .
					"'" . pip_string_sanitise( $row[17] ) . "', " .
					"'" . pip_string_sanitise( $row[18] ) . "', " .
					"'" . pip_string_sanitise( $row[19] ) . "', " .
					"'" . pip_string_sanitise( $row[20] ) . "', " .
					"'" . pip_string_sanitise( $row[21] ) . "', " .
					"'" . pip_string_sanitise( $row[22] ) . "')" );

		if ( !$result )
			throw new Exception('Failed to add testdata!');
	}
}
