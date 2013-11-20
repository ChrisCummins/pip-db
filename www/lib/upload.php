<?php

/*
 * Returns whether we're currently attempting to upload a new file.
 */
function pip_upload_new_file() {
	return PostActionValues::Upload == pip_post_get( PostVariables::Action )
		&& pip_files_isset( FilesVariables::Dataset );
}

function pip_upload_parse_file() {
	$super = pip_files( FilesVariables::Dataset );
	$path = $super['tmp_name'];
}
