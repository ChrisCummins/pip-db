#!/bin/bash

# Lookup the root directory for the project. If unable to locate root, exit
# script.
#
#     @return The absolute path to the project root directory
get_project_root() {
	local root=""

	while [[ "$(pwd)" != "/" ]]; do
		if test -f configure.ac; then
			echo "$(pwd)"
			return
		fi
		cd ..
	done

	echo "fatal: Unable to locate project base directory." >&2
	exit 3
}

main() {
	echo "$0"
}
main $@
