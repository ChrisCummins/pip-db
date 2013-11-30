#!/bin/bash

# The prefix for work-in-progress issue branches
ISSUE_BRANCH_PREFIX=feature/

# The branch to base work-in-progress branches off of
ISSUE_BRANCH_BASE=master

# The remote repository destination
REMOTE=origin

# Print program usage
usage() {
	echo "Usage: $0 <command> [args]"
	echo ""
	echo "Commands:"
	echo "       s | show <issue-number>  Show an issue number"
	echo "       n | new <issue-number>   Begin work on a new issue"
	echo "       p | pause                Pause work on the current issue"
	echo "       c | close                Complete work on the current issue"
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
#    @return Branch name, e.g. 'feature/28', 'master'
get_current_branch() {
	local refname=`git symbolic-ref HEAD 2>/dev/null`
	echo ${refname##refs/heads/}
}

# Get the issue number from the current feature branch
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

# Print a summary of the current issues and feature branches
show() {
	local branches=$(git branch | grep --color=never "$ISSUE_BRANCH_PREFIX")
	local issues=$(echo "$branches" | sed 's/.*feature\///')

	for i in $issues; do
		$(get_git_toplevel)/tools/ghi list | grep --color=never '^ *'$i
	done
}

# Create a new local work-in-progress branch
#    $1 issue number
new() {
	local issue=$1

	if [ -z "$issue" ]; then
		echo "fatal: no issue number given" >&2
		exit 4
	fi

	# Sanity checks
	execute "fail_if_issue_not_valid $issue" quiet

	$(get_git_toplevel)/tools/ghi show $issue

	# Perform branching
	execute "git flow feature start $issue"
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
	test -n "$have_stashed" && execute "git stash pop"

	# Output results
	echo ""
	echo "Merged '$branch' into '$ISSUE_BRANCH_BASE'"
}

# Close the current work-in-progress branch and rebase on master
close() {
	local branch=`get_current_branch`
	local issue=$(echo "$branch" | sed 's/.*feature\///')

	$(get_git_toplevel)/tools/ghi show $issue

	# Cleanup issue branch
	execute "git flow feature finish $issue"
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
	"show" | "s")
		shift
		show $@
		;;
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
