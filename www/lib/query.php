<?php

interface MySQLStatement
{
	public function get_mysql_query();
}

class Select implements MySQLStatement
{
	private $table;
	private $columns;
	private $where;
	private $prefix;
	private $suffix;

	public function __construct( $table, $columns, $where,
				     $prefix = "", $suffix = "" ) {
		$this->table = $table;
		$this->columns = $columns;
		$this->where = $where;
		$this->prefix = $prefix;
		$this->suffix = $suffix;
	}

	public function get_mysql_query() {
		$s = "SELECT $this->prefix ";

		foreach( $this->columns as $column )
			$s .= "$column, ";

		// Strip the last comma
		$s = preg_replace( '/, $/', ' ', $s );

		$s .= "FROM $this->table WHERE ";
		$s .= $this->where->get_mysql_query();

		$s .= " $this->suffix";

		return $s;
	}
}

abstract class ConditionLogic
{
	const LOGICAL_AND = "AND";
	const LOGICAL_OR = "OR";

	static function val() {
		return array(
			self::LOGICAL_AND,
			self::LOGICAL_OR
			);
	}
}

abstract class Condition implements MySQLStatement {}

class StringMatchCondition extends Condition {
	private $field;
	private $value;
	private $exact;

	public function __construct( $field, $value, $exact = False ) {
		$this->field = $field;
		$this->value = $value;
		$this->exact = $exact;
	}

	public function get_mysql_query() {
		if ( $this->exact )
			return "$this->field LIKE '$this->value'";
		else
			return "$this->field LIKE '%$this->value%'";
	}
}

class CompositeCondition extends Condition {
	private $conditions;
	private $logic;

	public function __construct( $logic, $conditions ) {

	}

	public function add_condition( $condition ) {

	}

	public function get_mysql_query() {
		return "";
	}
}

/*
 * The PipSearchQueryValues class.
 *
 * Once instantiated, it obtains all of the search query values from GET
 * variables, and provides an API for accessing them.
 */
class PipSearchQueryValues
{

	private $query;
	private $exactphrase;
	private $anyword;
	private $notword;
	private $source;
	private $location;
	private $ec1;
	private $ec2;
	private $ec3;
	private $ec4;
	private $pimin;
	private $pimax;
	private $molecularmin;
	private $molecularmax;
	private $experimental;
	private $temperaturemin;
	private $temperaturemax;
	private $record;
	private $startat;

	public function __construct() {

		$this->query = pip_get( GetVariables::Query );
		$this->exactphrase = pip_get ( GetVariables::QueryExactPhrase );
		$this->anyword = pip_get ( GetVariables::QueryAnyWord );
		$this->notword = pip_get ( GetVariables::QueryNotWord );
		$this->source = pip_get ( GetVariables::QuerySource );
		$this->location = pip_get ( GetVariables::QueryLocation );
		$this->ec1 = pip_get ( GetVariables::QueryEC1 );
		$this->ec2 = pip_get ( GetVariables::QueryEC2 );
		$this->ec3 = pip_get ( GetVariables::QueryEC3 );
		$this->ec4 = pip_get ( GetVariables::QueryEC4 );
		$this->pimin = pip_get ( GetVariables::QueryPiMin );
		$this->pimax = pip_get ( GetVariables::QueryPiMax );
		$this->molecularmin = pip_get ( GetVariables::QueryMolecularWeightMin );
		$this->molecularmax = pip_get ( GetVariables::QueryMolecularWeightMax );
		$this->experimental = pip_get ( GetVariables::QueryExperimentalMethod );
		$this->temperaturemin = pip_get ( GetVariables::QueryTemperatureMin );
		$this->temperaturemax = pip_get ( GetVariables::QueryTemperatureMax );
		$this->record = pip_get ( GetVariables::Record );
		$this->startat = pip_get ( GetVariables::StartAt );

	}

	protected function split( $query ) {
		return preg_split( '/\s/', pip_string_sanitise( $query ),
				   NULL, PREG_SPLIT_NO_EMPTY );
	}

	/*
	 * Returns an array of words which should ALL match in the protein's
	 * name (but not necessarily in order).
	 */
	public function get_query_words_all() {
		return $this->split( $this->query );
	}

	/*
	 * Returns an array of words, ANY of which should match in the protein's
	 * name.
	 */
	public function get_query_words_any() {
		return $this->split( $this->anyword );
	}

	/*
	 * Returns a string which must be EXACTLY matched in the protein's name.
	 */
	public function get_exactphrase() {
		return pip_string_sanitise( $this->exactphrase );
	}

	/*
	 * Returns an array of words, NONE of which should match in the
	 * protein's name.
	 */
	public function get_excluded_words() {
		return $this->split( $this->notword );
	}

	/*
	 * Returns a string to match the source of a protein.
	 */
	public function get_source() {
		return pip_string_sanitise( $this->source );
	}

	/*
	 * Returns a string to match the location/organ of a protein.
	 */
	public function get_location() {
		return pip_string_sanitise( $this->location );
	}

	public function get_ec1() {
		return pip_string_sanitise( $this->ec1 );
	}

	public function get_ec2() {
		return pip_string_sanitise( $this->ec2 );
	}

	public function get_ec3() {
		return pip_string_sanitise( $this->ec3 );
	}

	public function get_ec4() {
		return pip_string_sanitise( $this->ec4 );
	}

	public function get_pi_min() {
		return pip_string_sanitise( $this->pimin );
	}

	public function get_pi_max() {
		return pip_string_sanitise( $this->pimax );
	}

	public function get_mol_min() {
		return pip_string_sanitise( $this->molecularmin );
	}

	public function get_mol_max() {
		return pip_string_sanitise( $this->molecularmax );
	}

	/*
	 * Returns a string to match the experimental method of a record.
	 */
	public function get_experimental_method() {
		return pip_string_sanitise( $this->experimental );
	}

	public function get_temp_min() {
		return pip_string_sanitise( $this->temperaturemin );
	}

	public function get_temp_max() {
		return pip_string_sanitise( $this->temperaturemax );
	}

	public function get_record() {
		return pip_string_sanitise( $this->record );
	}

	public function get_start_at() {
		return pip_string_sanitise( $this->startat );
	}

}

abstract class PipQueryBuilder {

	static function build($query_values) {
		return "";
	}
}
