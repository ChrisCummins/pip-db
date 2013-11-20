<?php


class PipDbException extends Exception {}

/*
 * Initialise a connection with the database.
 */
function pip_db_init() {

	$status = mysql_connect( PipDatabase::Host,
				 PipDatabase::Username,
				 PipDatabase::Password );

	if ( !$status )
		throw new PipDbException( 'Failed to connect to with user credentials!' );

	$status = mysql_select_db( PipDatabase::Name );

	if ( !$status )
		throw new PipDbException( 'Failed to connect to dabase!' );

}

/*
 * Query the database.
 */
function pip_db_query( $query ) {
	return mysql_query( $query );
}

/*
 * Return the number of rows in a query.
 */
function pip_db_num_rows( $query ) {
	return mysql_num_rows( pip_db_query ( $query ) );
}

/*
 * Fetches rows for a given query.
 */
function pip_db_fetch_row( $query ) {
	$results = pip_db_query( $query );

	if ( !$results )
		throw new Exception('Query returned no results!');

	return mysql_fetch_row( $results );
}

/*
 * Create a new database table.
 */
function pip_db_table_create( $tbl_name, $definition,
			      $engine = PipDatabase::Engine ) {

	return pip_db_query( 'CREATE TABLE IF NOT EXISTS ' .
			     $tbl_name . '(' . $definition .
			     ') ENGINE=' . $engine );
}

/*
 * Delete a database table, if it exists.
 */
function pip_db_table_delete( $tbl_name ) {

	return pip_db_query ( 'DROP TABLE IF EXISTS ' . $tbl_name );
}
