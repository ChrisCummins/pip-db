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
	echo "           pause                Pause work on the current issue"
	echo "           close                Complete work on the current issue"
}

# Execute command if we're not on a dry-run, else just print the commnad
#     $1 Command to execute
#     $2 (optional) Set to 'quiet' to suppress output being printed on dry-run
execute() {
	local cmd=$1
	local quiet=$2

	test -z "$DRY_RUN" && {
		set -e
		$cmd;
		set +e
	} || {
		test -z $quiet && echo $cmd
	}
}

# Prints a msg to stdout if we're not on a dry-run
#    $1 msg
echo_if_live() {
	local msg=$1

	test -z "$DRY_RUN" && echo $1
}

# Check that the git tree is clean
#    @return 0 if clean, else 1
is_working_tree_clean() {
	if [[ `git diff --shortstat 2> /dev/null | tail -n1` == "" ]]; then
		return 0
	else
		return 1
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
	execute "fail_if_issue_not_valid $issue" quiet

	# Perform branching
	execute "git checkout -b wip/$issue $ISSUE_BRANCH_BASE"
	echo_if_live "git push -u $REMOTE $branch"
	execute "git push -u $REMOTE $branch"

	# Output results
	echo ""
	echo "Execute '$0 close' when completed."
}

# Switch back to master and rebase current issue work
pause() {
	local branch=`get_current_branch`

	# Sanity checks
	fail_if_not_on_issue_branch

	# Perform branching
	if ! is_working_tree_clean; then
		execute "git stash"
		local have_stashed=yes
	fi
	execute "git checkout $ISSUE_BRANCH_BASE"
	echo_if_live "git rebase $branch"
	execute "git rebase $branch"
	test -n "$have_stashed" && execute "git stash pop"

	# Output results
	echo ""
	echo "Merged '$branch' into '$ISSUE_BRANCH_BASE'"
}

# Close the current work-in-progress branch and rebase on master
close() {
	local branch=`get_current_branch`

	pause $@

	# Cleanup issue branch
	execute "git branch -D $branch"
	execute "git push $REMOTE :$branch"
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
	"new" | "n")
		shift
		new $@
		;;
	"pause" | "p")
		shift
		pause $@
		;;
	"close" | "c")
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
