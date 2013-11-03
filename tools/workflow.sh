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

# Check that the issue number is a valid open github issue else fail
#    $1 issue number
fail_if_issue_not_valid () {
	local issue=$1
	local is_valid=1 #TODO: Implement test

	if [[ $is_valid != 1 ]]; then
		echo "fatal: issue #$issue not found" >&2
		exit 3
	fi
}

# Get git toplevel directory
#    @return Path to git toplevel
get_git_toplevel() {
	git rev-parse --show-toplevel
}

# Get the name of the current branch
#    @return Branch name, e.g. 'wip/28', 'master'
get_current_branch() {
	local refname=`git symbolic-ref HEAD 2>/dev/null`
	echo ${refname##refs/heads/}
}

# Get the issue number from the current wip branch
#    @return Issue number, e.g. '28'
get_current_issue() {
	local branch=`get_current_branch`
	echo ${branch##$ISSUE_BRANCH_PREFIX}
}

# Check that the current branch is an issue branch else fail
fail_if_not_on_issue_branch() {
	local prefix=`echo $(get_current_branch) | grep "^$ISSUE_BRANCH_PREFIX"`

	if [ -z $prefix ]; then
		echo "fatal: not on an issue branch" >&2
		exit 5
	fi
}

# Create a new local work-in-progress branch
#    $1 issue number
new() {
	local issue=$1
	local branch=$ISSUE_BRANCH_PREFIX$issue

	if [ -z "$issue" ]; then
		echo "fatal: no issue number given" >&2
		exit 4
	fi

	# Sanity checks
	fail_if_issue_not_valid $issue
	fail_if_tree_not_clean

	# Perform branching
	set -e
	git checkout -b wip/$issue $ISSUE_BRANCH_BASE
	git push -u $REMOTE $branch
	set +e

	# Output results
	echo ""
	echo "Execute '$0 close' when completed."
}

# Close the current work-in-progress branch and rebase on master
close() {
	local branch=`get_current_branch`

	# Sanity checks
	fail_if_not_on_issue_branch
	fail_if_tree_not_clean

	# Perform branching
	set -e
	git checkout $ISSUE_BRANCH_BASE
	git rebase $branch
	git branch -D $branch
	git push $REMOTE :$branch
	set +e

	# Output results
	echo ""
	echo "Merged '$branch' into '$ISSUE_BRANCH_BASE'"
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
