<?php

require_once( $_SERVER['PHP_ROOT'] . 'init.php' );

pip_logout();
pip_goto( pip_history_get_referer() );
