#!/bin/bash

# The prefix for work-in-progress issue branches
ISSUE_BRANCH_PREFIX=wip/

# The branch to base work-in-progress branches off of
ISSUE_BRANCH_BASE=master

# The remote repository destination
REMOTE=origin

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
	if [[ `git diff --shortstat 2> /dev/null | tail -n1` != "" ]]; then
		echo "fatal: uncommitted changes in repository" >&2
		exit 2
	fi
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

	# Parse user input
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
