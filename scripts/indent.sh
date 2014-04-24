#!/usr/bin/env bash
#
# Automatically indent Clojure source files
#
# Iterates over every .clj file in the current directory and applies
# the Emacs clojure-mode indent-region on each.
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
elpa=~/.emacs.d/elpa/*/clojure-mode.el

test -f $elpa || {
    echo "fatal: can't find clojure-mode.el file!" >&2
    exit 2
}

el=$(echo $elpa | sed 's/\.el$//')

find "." -name '*.clj' | while read file; do
    emacs "$file" \
        --batch \
        --eval="(progn
                    (load \"$el\")
                    (require 'clojure-mode)
                    (clojure-mode)
                    (indent-region (point-min) (point-max))
                    (save-buffer))"
done
