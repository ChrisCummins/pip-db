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

class StringNotMatchCondition extends Condition {
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
			return "$this->field NOT LIKE '$this->value'";
		else
			return "$this->field NOT LIKE '%$this->value%'";
	}
}

class CompositeCondition extends Condition {
	private $conditions;
	private $logic;

	public function __construct( $logic = ConditionLogic::LOGICAL_AND,
				     $conditions = array() ) {
		$this->logic = $logic;
		$this->conditions = $conditions;
	}

	public function add_condition( $condition ) {
		return array_push( $this->conditions, $condition );
	}

	public function get_mysql_query() {
		$s = "(";

		foreach( $this->conditions as $condition )
			$s .= $condition->get_mysql_query() . " $this->logic ";

		// Strip the last logic
		$s = preg_replace( "/ " . $this->logic . " $/", '', $s );

		$s .= ")";

		return $s;
	}
}
