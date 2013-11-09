<?php

require_once( 'functions.php' );

$params = array(
	'name' => 'Krzysztof',
	'friends' => array(
		array(
			'firstname' => 'John',
			'lastname' => 'Smith'
		),
		array(
			'firstname' => 'Britney',
			'lastname' => 'Spears'
		),
		array(
			'firstname' => 'Brad',
			'lastname' => 'Pitt'
		)
	)
);

render_template( 'test', $params );
