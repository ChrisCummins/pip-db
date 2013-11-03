#!/bin/bash

# Print program usage
usage() {
	echo "Usage: $0 <command> [args]"
	echo ""
	echo "Commands:"
	echo "           new <issue-number>   Begin work on a new issue"
	echo "           close                Close current work an issue"
}

# Check that git tree is clean else fail
fail_if_tree_not_clean() {
	echo "clean?"
}

# Get git toplevel directory
#    @return Path to git toplevel
get_git_toplevel() {
	git rev-parse --show-toplevel
}

# Create a new local work-in-progress branch
#    $1 issue number
new() {
	fail_if_tree_not_clean
	echo "new in `get_git_toplevel`"
}

# Close the current work-in-progress branch and rebase on master
close() {
	fail_if_tree_not_clean
	echo "close in `get_git_toplevel`"
}

main() {
	test -n "$DEBUG" && {
		set -x
	}

	for arg in $@; do
		if [ "$arg" = "--help" ] || [ "$arg" = "-h" ]; then
			usage
			exit 0
		fi
	done

	case "$1" in
	"new")
		shift
		new $@
		;;
	"close")
		shift
		close $@
		;;
	*)
		echo "fatal: unrecognised command '$1'" >&2
		exit 1
		;;
	esac
}
main $@
