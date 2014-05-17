#!/usr/bin/env bash
#
# Update the BLAST+ binaries located in bin/. This fetches the latest
# NCBI release for x64 linux systems.
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

set -e

cd "$(get_project_root)"

echo "Checking latest BLAST+ release version..."

cd extern

# Get the path of the latest release
ftp -n ftp.ncbi.nlm.nih.gov >index.tmp <<END_SCRIPT
quote USER anonymous
quote PASS
cd blast/executables/blast+/LATEST
ls
quit
END_SCRIPT

FILE=`grep "x64-linux.tar.gz" index.tmp | awk '{print $NF}'`
rm -f index.tmp

test -n "$FILE" || { echo "Failed to retrieve path!"; exit 1; }

if [ -f "$FILE" ]; then
    echo "Using cached file '$FILE'"
else
    echo "Downloading '$FILE'..."
    wget "ftp.ncbi.nlm.nih.gov/blast/executables/blast+/LATEST/$FILE"
fi

DIR=`echo "$FILE" | sed 's/-x64-linux\.tar\.gz//'`

echo "Installing BLAST+ binaries..."

if [ ! -d "$DIR" ]; then
    echo "Unpacking '$FILE'..." | indent
    tar zxvf "$FILE" | indent
fi

cp -v "$DIR/bin/blastp" "$DIR/bin/makeblastdb" bin/

if [ -z "$1" ]; then
    echo "Tidying up...";
    rm -rfv "$FILE" "$DIR";
fi
