<?php

/*
 * Returns whether the site is in debugging mode or not.
 */
function pip_debugging() {
	return 'yes' == pip_server_get_debug();
}
