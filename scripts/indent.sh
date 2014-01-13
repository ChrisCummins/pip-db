#!/usr/bin/env bash
#
# Automatically indent Clojure source files
#
# Iterates over every .clj file in the current directory and applies
# the Emacs clojure-mode indent-region on each.

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
