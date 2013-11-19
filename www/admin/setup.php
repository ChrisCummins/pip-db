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

/* Create an admin account type. */
pip_db_query( "INSERT INTO user_types (type_name) VALUES ('admin')" );

pip_render_template( 'setup', $content );
