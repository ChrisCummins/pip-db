#!/bin/bash
#
# Establishes a development environment and runs a local instance of
# the server. Designed to a no-hassle means for quickly starting a
# hacking sesh.

set -e

export DEBUG=1
export PID_FILE=/tmp/pip-db.pid

# Export the datbase env, if not set
[ -n "$DATABASE_URL" ] || export DATABASE_URL=postgresql://localhost:5432/pip-db

# Launch a user database, if required
pgrep postgres >/dev/null 2>&1 || ./scripts/launch-db.sh &

# Kill existing java instances if server is running
test -f $PID_FILE && {
    # TODO: We should tidy this up, as it is extremely reckless to
    # just kill all Java instances like this.
    pkill java || echo "Failed to terminate old process"
    rm -f $PID_FILE
}

# Run the test suite
lein test

# Run the server
lein run &

# Create PID file
echo $! > $PID_FILE
