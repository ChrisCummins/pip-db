#!/bin/bash
#
# Establishes a development environment and runs a local instance of
# the server. Designed to a no-hassle means for quickly starting a
# hacking sesh.
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

set -e

# Export the datbase env, if not set
[ -n "$DATABASE_URL" ] || export DATABASE_URL=postgresql://localhost:5432/pip-db

# Launch a user database, if required
pgrep postgres >/dev/null 2>&1 || ./scripts/launch-db.sh &

# Kill existing java instances
#
# TODO: We should tidy this up, as it is extremely reckless to
# just kill all Java instances like this.
set +e
pkill java
set -e

# Kill existing instances of this script
#
# See: http://kheyali.blogspot.co.uk/2012/01/self-terminating-code-for-bash-scripts.html
processes=`ps aux | grep $0 | tr -s " " | cut -d" " -f2 `
for process in $(echo $processes)
do
    if [ "$process" -ne "$$" ]; then
        set +e
        kill -9 $process 2>/dev/null
        set -e
    fi
done

# Run the test suite
lein test

export DEBUG=1

# Run the server
lein run
