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

			'user_types' =>
"user_type_id bigint(20) UNSIGNED NOT NULL auto_increment,
type_name varchar(255) NOT NULL DEFAULT '',
PRIMARY KEY (user_type_id)",

			'users' =>
"user_id bigint(20) UNSIGNED NOT NULL auto_increment,
email varchar(200) NOT NULL DEFAULT '',
pass varchar(255) NOT NULL DEFAULT '',
user_type_id bigint(20) UNSIGNED NOT NULL,
PRIMARY KEY (user_id),
INDEX (user_type_id),
FOREIGN KEY (user_type_id) REFERENCES user_types(user_type_id)",

			'records' =>
"record_id bigint(20) UNSIGNED NOT NULL auto_increment,
dataset varchar(255),
ec varchar(255),
name varchar(255),
alt_name varchar(255),
source varchar(255),
organ varchar(255),
mw varchar(255),
sub_no varchar(255),
sub_mw varchar(255),
no_iso varchar(255),
pi_max varchar(255),
pi_range_min varchar(255),
pi_range_max varchar(255),
pi_major varchar(255),
pi varchar(255),
temp varchar(255),
method varchar(255),
valid varchar(255),
sequence varchar(255),
species varchar(255),
citations varchar(255),
abstract varchar(255),
pubmed varchar(255),
notes varchar(255),
PRIMARY KEY (record_id)"

			);
	}

}
