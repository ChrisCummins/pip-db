#!/usr/bin/env bash
#
# Create a submission archive
#
# This script mirrors the project repository and cleans it up,
# preparing it for submission for academic assessment.
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
    echo "Usage: $0 <target>"
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

# A list of files and directories to remove after mirroring
CLEANFILES=".git
.lein-failures
.lein-repl-history
.nrepl-port
aclocal.m4
autom4te.cache
build/install-sh
build/missing
configure
db/
extern/bin/*
extern/ncbi-blast-*
pg/"

# Generate a submission directory
do_mksubmission() {
    local target="$1"

    test ! -d "$target" || { echo "Target '$target' already exists!" >&2; exit 1; }

    set -e
    mkdir -p "$target"

    cd "$(get_project_root)"

    echo "Creating submission '$target'"
    rsync -a . "$target/"

    cd "$target"
    echo "Building submission files"
    ./autogen.sh >/dev/null
    ./configure >/dev/null
    make -C Documentation >/dev/null

    # Export LaTeX PDFs
    mv Documentation/plan/ProjectPlan.pdf  Documentation/ProjectPlan.pdf
    mv Documentation/evaluation/report.pdf Documentation/Evaluation.pdf
    mv Documentation/midterm/report.pdf    Documentation/MidtermReport.pdf
    mv Documentation/design/d1/mockups.pdf Documentation/D1Mockups.pdf
    mv Documentation/design/d2/renders.pdf Documentation/D2Mockups.pdf

    # Export markdown HTMLs
    for f in $(find Documentation -name '*.md'); do
        html="${f%.*}".html
        echo "  Creating $html"
        pandoc $f -o $html
        rm $f
    done;

    echo "Cleaning submission repository"
    make distclean >/dev/null

    # Tidy up
    for f in $CLEANFILES; do
        rm -rf $f;
    done;

    find . -name 'Makefile'    | xargs rm -f
    find . -name 'Makefile.in' | xargs rm -f
    find . -name '.gitignore'  | xargs rm -f

    # Create ZIP file
    directory=${PWD##*/}
    cd ..
    test ! -f "$directory.zip" || { echo "Archive '$target.zip' already exists!" >&2; exit 2; }
    echo "Creating '$directory.zip' archive"
    zip -r "$directory.zip" "$directory" >/dev/null
    du -h "$directory.zip"
    md5sum "$directory.zip"
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

    do_mksubmission "$1"
}
main $@
