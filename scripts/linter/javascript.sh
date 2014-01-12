#!/usr/bin/env bash
#
# Run jslint on JavaScript project resources
#
set -e

jslint=node_modules/jslint/bin/jslint.js

test -f "$jslint" || npm install jslint >/dev/null 2>&1

find resources/js/ -name '*.js' \
    | grep -v .min.js \
    | grep -v google-analytics.inline.js \
    | xargs $jslint --terse --browser --predef $,jQuery,moment,window,document
