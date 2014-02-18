#!/bin/bash
#
# Establishes a development environment and runs a local instance of
# the server. Designed to a no-hassle means for quickly starting a
# hacking sesh.

set -e

export DEBUG=1

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


# Run the server
lein run
