#!/bin/bash

# Print program usage
usage() {
	echo "Usage: $0 <version>"
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
