<?php

/*
 * Global config constants
 */
abstract class PipDatabase {

	const Host = 'localhost';
	const Username = 'pipdb';
	const Password = 'ou0aiWiwjohLaen5';
	const Name = 'pipdb';
	const Engine = 'INNODB';

	static function schema() {
		return array(
			'users' =>
"user_id bigint(20) UNSIGNED NOT NULL auto_increment,
email varchar(200) NOT NULL DEFAULT '',
pass varchar(255) NOT NULL DEFAULT '',
user_type_id bigint(20) UNSIGNED NOT NULL,
PRIMARY KEY (user_id),
INDEX (user_type_id),
FOREIGN KEY (user_type_id) REFERENCES user_types(user_type_id)",
			'user_types' =>
"user_type_id bigint(20) UNSIGNED NOT NULL auto_increment,
type_name varchar(255) NOT NULL DEFAULT '',
PRIMARY KEY (user_type_id)"
			);
	}

}
