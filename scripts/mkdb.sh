#!/usr/bin/env bash
#
# Setup a local instance of the postgres db.
#
topsrcdir=pip-db # Top level directory
dir=pg           # Database directory
dbname=pip-db    # Database name

# Look up the top source directory. Do this by traversing up the directory tree
# from the current directory. If we haven't found the top directory by the time
# we reach root (/), fail.
get_source_directory() {
    while [[ "$(pwd)" != "/" ]]; do
        if [[ "${PWD##*/}" = "$topsrcdir" ]]; then
            pwd
            return
        fi
        cd ..
    done

    echo "fatal: Unable to locate source directory." >&2

    # If we can't find a real path to change to, then return something bogus
    # so that cd will fail and the script will terminate.
    echo "/not/a/real/path"
}

set -e

cd "$(get_source_directory)" 2>/dev/null

echo "Creating PostreSQL database cluster in '$dir'..."

test -d $dir && { echo "Directory '%dir' already exists!"; exit 5; }

initdb $dir

echo "Starting PostgreSQL server..."
postgres -D $dir &

echo "Creating PostgreSQL database '$dbname'..."
createdb $dbname
