#!/bin/sh
#
# Launch a local instance of the server.

export DATABASE_URL=postgresql://localhost:5432/pip-db

lein run
