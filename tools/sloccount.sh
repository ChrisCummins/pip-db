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

# Returns a list of paths to files which match the pattern *.$1, relative to the
# project root directory. This excludes files from the build/www directory.
#
#     $1 The file extension to match
#     $2 (optional) The subdirectory to look in, defaults to the project root
find_files_with_extension() {
	local ext="$1"
	local subdir="/$2"

	cd "$(get_project_root)"

	find ".$subdir" -type f -name '*.'"$ext" | grep -v '/build/www/' | sort
}

# Prints the sloccounts for a list of files, followed by a total.
#
#     $1 A space-separated list of file paths
print_sloccount_for_files() {
	local files="$@"
	local total=0

	cd "$(get_project_root)"

	for f in $files; do
		local sloccount=$(wc -l "$f" | awk '{print $1}')

		total=$((total+sloccount))

		echo -e "    $sloccount   \t$f"
	done

	echo -e "    $total   \ttotal"
}

main() {
	echo "$0"
}
main $@
