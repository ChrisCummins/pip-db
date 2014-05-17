#!/usr/bin/env bash
#
# Setup a local instance of the postgres db.
#
# Copyright 2014 Chris Cummins.
#
# This file is part of pip-db.
#
# pip-db is free software: you can redistribute it and/or modify it
# under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# pip-db is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with pip-db.  If not, see <http://www.gnu.org/licenses/>.
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
