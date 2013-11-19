#!/bin/bash

# Print program usage
usage() {
	echo "Usage: $0 <version>"
}

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

# Given a version string in the form <major>.<minor>.<micro>,
# return the major component.
get_major() {
	echo $1 | sed -r 's/^([0-9]+)\.[0-9]+\.[0-9]+$/\1/'
}

# Given a version string in the form <major>.<minor>.<micro>,
# return the minor component.
get_minor() {
	echo $1 | sed -r 's/^[0-9]+\.([0-9]+)\.[0-9]+$/\1/'
}

# Given a version string in the form <major>.<minor>.<micro>,
# return the micro component.
get_micro() {
	echo $1 | sed -r 's/^[0-9]+\.[0-9]+\.([0-9]+)$/\1/'
}

# Find and return the current version string in the form <major>.<minor>.<micro>
get_current_version() {
	local major=$(grep 'm4_define(\s*\[pipdb_major_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')
	local minor=$(grep 'm4_define(\s*\[pipdb_minor_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')
	local micro=$(grep 'm4_define(\s*\[pipdb_micro_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')

	echo $major.$minor.$micro
}

# Given a version string in the form <major>.<minor>.<micro>,
# verify that it is correct.
#
#     @return 0 if version is valid, else 1
verify_version() {
	local version="$1"

	local major=$(get_major $version)
	local minor=$(get_minor $version)
	local micro=$(get_micro $version)

	test -n $major || return 1;
	test -n $minor || return 1;
	test -n $micro || return 1;

	return 0;
}

main() {
	# Set debugging output if DEBUG=1
	test -n "$DEBUG" && {
		set -x
	}

	# Check for help argument and print usage
	for arg in $@; do
		if [ "$arg" = "--help" ] || [ "$arg" = "-h" ]; then
			usage
			exit 0
		fi
	done

	# Check for new version argument
	if test -z "$1"; then
		usage
		exit 1
	fi
}
main $@
