<?php

echo "<h1>Server Sanity Check</h1>\n\n";

echo "<ul>\n";
echo '<li><a href="#server">$_SERVER</a></li>';
echo '<li><a href="#apache">Apache Modules</a></li>';
echo "</ul>\n";

echo '<h2 id="server">$_SERVER Superglobals</h2>\n';

echo "<table>\n";
foreach($_SERVER as $key => $value) {
	echo "<tr>\n";
	echo "<td>$key</td><td>$value</td>\n";
	echo "</tr>\n";
}
echo "</table>\n";

echo '<h2 id="apache">Apache Modules</h2>';
echo "<ul>\n";
foreach(apache_get_modules() as $key) {
	echo "<li>$key</li>\n";
}
echo "</ul>\n";
