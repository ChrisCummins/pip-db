#!/usr/bin/env bash
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

# Print program usage
usage() {
    echo "Usage: $0 <version>"
}

# Lookup the root directory for the project. If unable to locate root,
# exit script.
#
#     @return The absolute path to the project root directory
get_project_root() {
    while [[ "$(pwd)" != "/" ]]; do
        if test -f configure.ac; then
            pwd
            return
        fi
        cd ..
    done

    echo "fatal: Unable to locate project base directory." >&2
    exit 3
}

# Given a version string in the form <major>.<minor>.<micro>, return
# the major component.
#
#     @return Major component as an integer, e.g. '5'
get_major() {
    echo "$1" | sed -r 's/^([0-9]+)\.[0-9]+\.[0-9]+$/\1/'
}

# Given a version string in the form <major>.<minor>.<micro>, return
# the minor component.
#
#     @return Minor component as an integer, e.g. '5'
get_minor() {
    echo "$1" | sed -r 's/^[0-9]+\.([0-9]+)\.[0-9]+$/\1/'
}

# Given a version string in the form <major>.<minor>.<micro>, return
# the micro component.
#
#     @return Micro component as an integer, e.g. '5'
get_micro() {
    echo "$1" | sed -r 's/^[0-9]+\.[0-9]+\.([0-9]+)$/\1/'
}

# Find and return the current version string in the form
# <major>.<minor>.<micro>
#
#     @return Current version string, e.g. '0.1.4'
get_current_version() {
    cd "$(get_project_root)"

    local major=$(grep 'm4_define(\s*\[pipdb_major_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')
    local minor=$(grep 'm4_define(\s*\[pipdb_minor_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')
    local micro=$(grep 'm4_define(\s*\[pipdb_micro_version\]' configure.ac | sed -r 's/^.*([0-9]+).*$/\1/')

    echo "$major.$minor.$micro"
}

# Replace the project version with a new one.
#
#     @param $1 The new version string
set_new_version() {
    local new=$1

    local major="$(get_major "$new")"
    local minor="$(get_minor "$new")"
    local micro="$(get_micro "$new")"

    cd "$(get_project_root)"

    echo "Updating version string... 'configure.ac'"
    test -f configure.ac || { echo "fatal: 'configure.ac' not found!"; exit 3; }
    sed -r -i 's/(.*m4_define\(\s*\[pipdb_major_version\],\s*\[)[0-9]+(\].*)/\1'"$major"'\2/' configure.ac
    sed -r -i 's/(.*m4_define\(\s*\[pipdb_minor_version\],\s*\[)[0-9]+(\].*)/\1'"$minor"'\2/' configure.ac
    sed -r -i 's/(.*m4_define\(\s*\[pipdb_micro_version\],\s*\[)[0-9]+(\].*)/\1'"$micro"'\2/' configure.ac

    echo "Updating version string... 'project.clj'"
    test -f project.clj || { echo "fatal: 'project.clj' not found!"; exit 3; }
    sed -r -i 's/(\s*\(defproject\s+pip-db\s+")([0-9\.]+)(")/\1'"$major.$minor.$micro"'\3/' project.clj
}

# Make the git version bump commit.
#
#     @param $1 The new version string
make_version_bump_commit() {
    local new_version=$1

    cd "$(get_project_root)"

    echo "Creating version bump commit... '$new_version'"
    git add configure.ac
    git add project.clj
    git commit --allow-empty -m "Bump release version for '$new_version'" >/dev/null
}

# Perform the new release.
#
#     @param $1 New version string
do_mkrelease() {
    local new_version=$1

    echo -n "Getting current version... "
    local current_version=$(get_current_version)
    echo "'$current_version'"

    set_new_version "$new_version"
    make_version_bump_commit "$new_version"
}

# Given a version string in the form <major>.<minor>.<micro>, verify
# that it is correct.
#
#     @return 0 if version is valid, else 1
verify_version() {
    local version="$1"

    local major="$(get_major "$version")"
    local minor="$(get_minor "$version")"
    local micro="$(get_micro "$version")"

    test -n "$major" || return 1;
    test -n "$minor" || return 1;
    test -n "$micro" || return 1;

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

    # Sanity-check on supplied version string
    if ! verify_version "$1"; then
        echo "Invalid version string!" >&2
        exit 1
    fi

    do_mkrelease "$1"
}
main $@
