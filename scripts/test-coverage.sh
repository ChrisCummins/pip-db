#!/usr/bin/env bash
#
# Generate and view test coverage report.
#
set -e

echo "Generating test coverage report..."
lein cloverage >/dev/null

echo "Opening report..."
xdg-open target/coverage/index.html >/dev/null 2>&1 &
