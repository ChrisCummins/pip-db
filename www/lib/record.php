<?php

class Pip_Record
{
	private $id;
        private $names;
	private $properties;
	private $links;
	private $ref;

	public function __construct( $id ) {

		$this->id = $id;

		$result = pip_db_query( "SELECT * FROM records WHERE record_id='$id'" );
		if ( !$result )
			throw new Exception( 'Failed to retrieve Record by ID' );

		$assoc =  mysqL_fetch_assoc ( $result );
		if ( !$result )
			throw new Exception( 'Failed to retrieve resource' );

		$this->names = array( $assoc['name'], $assoc['alt_name'] );

		$this->properties = array(
			"Enzyme Commission number" => $assoc['ec'],
			"Source" => $assoc['source'],
			"Location" => $assoc['organ'],
			"pI" => $assoc['pi'],
			"Molecular weight" => $assoc['mw'],
			"Sub unit no" => $assoc['sub_no'],
			"Sub unit MW" => $assoc['sub_mw'],
			"Number of Iso Enzymes" => $assoc['no_iso'],
			"Valid sequences available" => $assoc['valid'],
			"Method" => $assoc['method'],
			"Temperature" => $assoc['temp']
			);

		$this->links = array(
			"Full Text" => $assoc['citations'],
			"Abstract" => $assoc['abstract'],
			"PubMed" => $assoc['pubmed'],
			"Species Taxonomy" => $assoc['species'],
			"Protein Sequence" => $assoc['sequence']
			);

		$this->ref = array(
			"author" => 'pip-db',
			"year" => '2013',
			"title" => $this->names[0],
			"site" => 'Protein Isoelectric Point Database',
			"href" => 'http://' . $_SERVER['SERVER_NAME'] . $_SERVER['REQUEST_URI']
			);
}

	public function get_names() {
		return $this->names;
	}

	public function get_properties() {
		return $this->properties;
	}

	public function get_meta() {
		/*
		 * TODO: Implement event time-stamping, allowing real meta data.
		 */
		return array(
			"added" => "on March 10th 2012",
			"edited" => "6 hours ago"
			);
	}

	public function get_links() {
		return $this->links;
	}

	public function get_ref() {
		return $this->ref;
	}
}
