<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

$content = array( "tables" => array() );

foreach ( PipDatabase::schema() as $table => $description ) {

	/* Add table to template content. */
	array_push( $content["tables"], array( "name" => $table,
					       "schema" => $description ) );

	/* Create database table. */
	if ( !pip_db_table_create( $table, $description ) ) {
		throw new PipDbException('Failed to create table: \'' . $table + '\'!' );
	}

}

$handle = fopen( $_SERVER['DOCUMENT_ROOT'] . '/testdata.csv', 'r' );

$content['test_data'] = array();

if ( $handle ) {

	while ( false !== ($line = fgets( $handle ) ) ) {
		$row = str_getcsv( $line, '	' );

		array_push( $content['test_data'], $row);

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

/* Create an admin account type. */
pip_db_query( "INSERT INTO user_types (type_name) VALUES ('admin')" );

pip_render_template( 'setup', $content );
