#!/usr/bin/env bash
#
# Run shellcheck linter on project shell scripts
#

cat > filelist <<EOF
./bin/build
EOF

find . -name '*.sh' >> filelist

cat filelist | \
    grep -v build/node | \
    grep -v node_modules | \
    xargs shellcheck -f gcc

rm -f filelist
