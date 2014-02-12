#!/bin/sh
#
# Start a subshell with the development environment set.

export DATABASE_URL=postgresql://localhost:5432/pip-db
export ENV_NAME="pip-db"

echo "Starting a development shell..."
$SHELL
