<?php require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

/*
 * The logout action form.
 *
 * FIXME: Should this really be a top-level script? This would probably be
 * better hidden in a subdirectory.
 */

pip_logout();
pip_goto( pip_history_get_referer() );
