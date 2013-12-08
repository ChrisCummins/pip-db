<?php

/*
 * The Query class.
 *
 * Provides a model of user query.
 */
class Pip_Query
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

	public function get_query_words_all() {
		return $this->split( $this->query );
	}

	public function get_query_words_any() {
		return $this->split( $this->anyword );
	}

	public function get_exactphrase() {
		return pip_string_sanitise( $this->exactphrase );
	}

	public function get_excluded_words() {
		return $this->split( $this->notword );
	}

	public function get_source() {
		return pip_string_sanitise( $this->source );
	}

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

	public function get_experimentatl() {
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
