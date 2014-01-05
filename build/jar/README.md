# Java archives

This directory contains third-party Java archive files needed during the build
process.

## Closure Compiler
    Closure Compiler (http://code.google.com/closure/compiler)
    License: Apache License 2.0
    Version: v20130411-27-ge76f6e4
    Built on: 2013/04/29 16:51

The Closure Compiler is a tool for making JavaScript download and run faster. It
is a true compiler for JavaScript. Instead of compiling from a source language
to machine code, it compiles from JavaScript to better JavaScript. It parses
your JavaScript, analyzes it, removes dead code and rewrites and minimizes
what's left. It also checks syntax, variable references, and types, and warns
about common JavaScript pitfalls.

## YUI Compressor
    YUI COmpressor (https://github.com/yui/yuicompressor)
    License: BSD (revised) open source license
    Version: Version: 2.4.8pre

The YUI Compressor is a JavaScript compressor which, in addition to removing
comments and white-spaces, obfuscates local variables using the smallest
possible variable name. This obfuscation is safe, even when using constructs
such as 'eval' or 'with' (although the compression is not optimal is those
cases) Compared to jsmin, the average savings is around 20%.
