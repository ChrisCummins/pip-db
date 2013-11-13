#!/bin/sh

## Tools we need:
## Note that we respect the values of AUTOCONF etc, like autoreconf does.
progs="autoconf automake"

## Minimum versions we need:
autoconf_min=$(sed -n 's/^ *AC_PREREQ(\([0-9\.]*\)).*/\1/p' configure.ac)

automake_min=$(sed -n 's/^ *AM_INIT_AUTOMAKE( *\[\? *\([0-9\.]*\).*/\1/p' configure.ac)

test -n "$autoconf_min" || exit 127
test -n "$automake_min" || exit 127

autoreconf --install

test -n "$srcdir" || srcdir=`dirname "$0"`
test -n "$srcdir" || srcdir=.
(
        cd "$srcdir" &&
        autoreconf --force --verbose --install
) || exit
test -n "$NOCONFIGURE" || "$srcdir/configure" "$@"
