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

# Build resources
make -s

# Run the test suite
lein test

# Run the server
lein run
