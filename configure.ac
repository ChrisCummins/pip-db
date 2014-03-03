AC_PREREQ(2.65)

dnl ================================================================
dnl     Autoconf script for pip-db.
dnl
dnl To rebuild the configure script from this, execute command
dnl     autoconf
dnl in the directory containing this script.
dnl
dnl
dnl Table of contents:
dnl
dnl   1.     Project versions
dnl
dnl   2.     Project details
dnl
dnl   3.     Substitutions
dnl   3.1.     Set the PREFIX
dnl   3.2.     Export the package versioning
dnl   3.3.     Export the locations of compiler JARs
dnl   3.4.     Export the git configuration
dnl
dnl   4.     Build requirements
dnl   4.1.     Do we have a Java runtime environment?
dnl   4.2.     Check for public resources
dnl   4.3.     Install node and npm
dnl   4.4.     Install Less CSS compiler
dnl
dnl   5.     Resources
dnl   5.1.     Are we minifying built CSS?
dnl   5.2.     Are we minifying built JS?
dnl
dnl   7.     Tools
dnl   7.1.     Are we building the tools?
dnl   7.2.     Do we have npm?
dnl   7.3.     Do we have node?
dnl   7.4.     Do we have optimist?
dnl   7.5.     Do we have python?
dnl   7.6.     Do we have the python git module?
dnl   7.7.     Do we have the python dateutil module?
dnl   7.8.     Install watchr requirements
dnl   7.9.     Install csv2yaps dependencies
dnl
dnl   8.     Documentation
dnl   8.1.     Are we build the documentation?
dnl   8.2.     Do we have pdflatex installed?
dnl   8.3.     Do we have pandoc installed?
dnl   8.4.     Do we have doctoc installed?
dnl   8.5.     Should xdg-open be used to open PDFs?
dnl   8.6.     Should emacsclient be used for editing files?
dnl
dnl   9.     Git
dnl   9.1.     Are we installing the project git hooks?
dnl
dnl   10.    Generate files
dnl   10.1.    Generate a config summary document
dnl   10.2.    Dah dah!
dnl
dnl ================================================================


dnl ================================================================
dnl 1. Project versions (i.e. the package version)
dnl ================================================================
m4_define([pipdb_major_version],[0])
m4_define([pipdb_minor_version],[5])
m4_define([pipdb_micro_version],[4])
m4_define([pipdb_version],[pipdb_major_version.pipdb_minor_version.pipdb_micro_version])


dnl ================================================================
dnl 2. Project details (i.e. the "meta" stuff)
dnl ================================================================
m4_define([author],[chrisc.101@gmail.com])

AC_INIT([pip-db],[pipdb_version],[author])
AC_CONFIG_SRCDIR([src/pip_db/core.clj])
AC_CONFIG_AUX_DIR([build])

dnl Support for --program-prefix, --program-suffix and
dnl --program-transform-name options
AC_ARG_PROGRAM

dnl Fairly arbitrary, older versions might work too.
AM_INIT_AUTOMAKE([1.11 -Wall foreign no-define])
AM_SILENT_RULES([yes])


dnl ================================================================
dnl 3. Substitutions (i.e. what we need to substitute in other files)
dnl ================================================================


dnl     ============================================================
dnl     3.1. Set the PREFIX
dnl     ============================================================
AS_IF([test "x$prefix" = "xNONE"],
      [prefix="$HOME/.local"])

PREFIX=$prefix
AC_SUBST([PREFIX])


dnl     ============================================================
dnl     3.2. Export the package versioning
dnl     ============================================================
AC_SUBST([PIPDB_MAJOR_VERSION],[pipdb_major_version])
AC_SUBST([PIPDB_MINOR_VERSION],[pipdb_minor_version])
AC_SUBST([PIPDB_MICRO_VERSION],[pipdb_micro_version])
AC_SUBST([PIPDB_VERSION],[pipdb_version])


dnl     ============================================================
dnl     3.3. Export the locations of compiler JARs
dnl     ============================================================
m4_define([jar_dir], ["$(pwd)/build/jar"])

m4_define([css_jar], [jar_dir/yuicompressor.jar])
m4_define([js_jar],  [jar_dir/closure-compiler.jar])

AC_SUBST([CSS_JAR],  [css_jar])
AC_SUBST([JS_JAR],   [js_jar])


dnl     ============================================================
dnl     3.4. Export the git configuration
dnl     ============================================================
AC_SUBST([GIT_RESERVED_BRANCHES],[stable])


dnl ================================================================
dnl 4. Build requirements
dnl ================================================================
AC_PROG_AWK
AC_PROG_INSTALL
AC_PROG_MKDIR_P
AC_PROG_SED


dnl     ============================================================
dnl     4.1. Do we have a Java runtime environment?
dnl     ============================================================
AC_CHECK_PROG([JAVA],[java],[java])
AS_IF([test "x$JAVA" != x],
      [HAVE_JAVA=yes],
      [HAVE_JAVA=no
       AC_MSG_ERROR([No Java virtual machine found.])])
AC_SUBST([HAVE_JAVA],[$HAVE_JAVA])
AM_CONDITIONAL([HAVE_JAVA],[test "x$HAVE_JAVA" = xyes])


dnl     ============================================================
dnl     4.2. Check for public resources
dnl     ============================================================
m4_define([public_resources],["$(pwd)/resources/public"])

AC_CHECK_FILE([public_resources],,[mkdir -pv public_resources])

AC_SUBST([PUBLIC_RESOURCES],[public_resources])


dnl     ============================================================
dnl     4.3. Install node and npm
dnl     ============================================================
dnl
dnl Snippet to install node and npm, taken from heroku nodejs buildpack. See:
dnl https://github.com/heroku/heroku-buildpack-nodejs/blob/master/bin/compile
dnl
AC_CHECK_FILE([build/node/bin/node],,
              [node_version=0.10.24
               # Download node from Heroku's S3 mirror of nodejs.org/dist
               node_url="http://s3pository.heroku.com/node/v$node_version/node-v$node_version-linux-x64.tar.gz"
               curl $node_url -s -o - | tar xzf - -C build

               # Move node (and npm) into ./build/node and make them executable
               mv build/node-v$node_version-linux-x64 build/node
               chmod +x build/node/bin/*])

NODE_BIN_DIR=$(pwd)/build/node/bin
AC_SUBST([NODE_BIN_DIR],[$NODE_BIN_DIR])


dnl     ============================================================
dnl     4.4. Install Less CSS compiler
dnl     ============================================================
AC_CHECK_FILE([node_modules/less/bin/lessc],,[build/node/bin/npm install less])

LESSC_BIN_DIR=$(pwd)/node_modules/less/bin
AC_SUBST([LESSC_BIN_DIR],[$LESSC_BIN_DIR])


dnl ================================================================
dnl 5. Resources
dnl ================================================================


dnl     ============================================================
dnl     5.1. Are we minifying built CSS?
dnl     ============================================================
AC_ARG_ENABLE([minify_css],
              [AS_HELP_STRING([--enable-minify-css],
                             [Minify compiled CSS sources (default: yes)])],,
              [enable_minify_css=yes])
AM_CONDITIONAL(ENABLE_MINIFY_CSS,[test "x$enable_minify_css" = xyes])


dnl     ============================================================
dnl     5.2. Are we minifying built JS?
dnl     ============================================================
AC_ARG_ENABLE([minify_js],
              [AS_HELP_STRING([--enable-minify-js],
                              [Minify compiled JS sources (default: yes)])],,
              [enable_minify_js=yes])
AM_CONDITIONAL(ENABLE_MINIFY_JS,[test "x$enable_minify_js" = xyes])


dnl ================================================================
dnl 7. Tools
dnl ================================================================


dnl     ============================================================
dnl     7.1. Are we building the tools?
dnl     ============================================================
AC_ARG_ENABLE(tools,
              [AS_HELP_STRING([--enable-tools],
                              [Build extra tools (default: yes)])],,
              [enable_tools=yes])
AM_CONDITIONAL(ENABLE_TOOLS,[test "x$enable_tools" = xyes])


dnl     ============================================================
dnl     7.2. Do we have npm?
dnl     ============================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_CHECK_PROG([NPM],[npm],[npm])
       AS_IF([test "x$NPM" != x],
             [HAVE_NPM=yes],
             [HAVE_NPM=no
              AC_MSG_ERROR([Node package manager not found.])])],
       [HAVE_NPM=no])
AC_SUBST([HAVE_NPM],[$HAVE_NPM])
AM_CONDITIONAL([HAVE_NPM],[test "x$HAVE_NPM" = xyes])


dnl     ============================================================
dnl     7.3. Do we have node?
dnl     ============================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_CHECK_PROG([NODE],[node],[node])
       AS_IF([test "x$NODE" != x],
             [HAVE_NODE=yes],
             [HAVE_NODE=no
              AC_MSG_ERROR([Node.js not found.])])],
       [HAVE_NODE=no])
AC_SUBST([HAVE_NODE],[$HAVE_NODE])
AM_CONDITIONAL([HAVE_NODE],[test "x$HAVE_NODE" = xyes])


dnl         ========================================================
dnl         7.4. Do we have optimist?
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_MSG_CHECKING([for optimist package])
       AS_IF([test -n "$(npm ls -g 2>/dev/null | grep optimist)"],
             [HAVE_NODE_OPTIMIST=yes],
             [HAVE_NODE_OPTIMIST=no])
       AC_MSG_RESULT([$HAVE_NODE_OPTIMIST])

       AS_IF([test "x$HAVE_NODE_OPTIMIST" != xyes],
             [AC_MSG_ERROR([Required node pacakge 'optimist' not found.])])],
      [HAVE_NODE_OPTIMIST=no])
AC_SUBST([HAVE_NODE_OPTIMIST],[$HAVE_NODE_OPTIMIST])
AM_CONDITIONAL([HAVE_NODE_OPTIMIST],[test "x$HAVE_NODE_OPTIMIST" = xyes])


dnl         ========================================================
dnl         7.5. Do we have python?
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AM_PATH_PYTHON([2.5])])


dnl         ========================================================
dnl         7.6. Do we have the python git module?
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_MSG_CHECKING([for python git module])
       AS_IF([`python -c 'from git import Repo;' 2>/dev/null`],
             [AC_MSG_RESULT([ok])],
             [AC_MSG_RESULT([no])
              AC_MSG_ERROR([Required Python module 'git' is not found])])])


dnl         ========================================================
dnl         7.7. Do we have the python dateutil module?
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_MSG_CHECKING([for python dateutil module])
       AS_IF([`python -c 'import dateutil.relativedelta;' 2>/dev/null`],
             [AC_MSG_RESULT([ok])],
             [AC_MSG_RESULT([no])
              AC_MSG_ERROR([Required Python module 'dateutil' is not found])])])


dnl         ========================================================
dnl         7.8. Install watchr requirements
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_CHECK_FILE([tools/watchr/node_modules],,
                     [cd tools/watchr && npm install])])


dnl         ========================================================
dnl         7.9. Install csv2yaps dependencies
dnl         ========================================================
AS_IF([test "x$enable_tools" = xyes],
      [AC_CHECK_FILE([tools/csv2yaps/node_modules],,
                     [cd tools/csv2yaps && npm install])])


dnl ================================================================
dnl 8. Documentation
dnl ================================================================


dnl     ============================================================
dnl     8.1. Are we building the documentation?
dnl     ============================================================
AC_ARG_ENABLE([documentation],
              [AS_HELP_STRING([--enable-documentation],
                              [Build extra documentation (default: no)])],,
              [enable_documentation=yes])
AM_CONDITIONAL(ENABLE_DOCUMENTATION,[test "x$enable_documentation" = xyes])


dnl     ============================================================
dnl     8.2. Do we have pdflatex installed?
dnl     ============================================================
AS_IF([test "x$enable_documentation" = xyes],
      [AC_CHECK_PROG([PDFLATEX],[pdflatex],[pdflatex])
       AS_IF([test "x$PDFLATEX" != x],
             [HAVE_PDFLATEX=yes],
             [HAVE_PDFLATEX=no
              AC_MSG_WARN([Unable to create PDF documentation.])])],
      [HAVE_PDFLATEX=no])
AC_SUBST([HAVE_PDFLATEX],[$HAVE_PDFLATEX])
AM_CONDITIONAL([HAVE_PDFLATEX],[test "x$HAVE_PDFLATEX" = xyes])


dnl     ============================================================
dnl     8.3. Do we have pandoc installed?
dnl     ============================================================
AS_IF([test "x$enable_documentation" = xyes],
      [AC_CHECK_PROG([PANDOC],[pandoc],[pandoc])
       AS_IF([test "x$PANDOC" != x],
             [HAVE_PANDOC=yes],
             [HAVE_PANDOC=no
              AC_MSG_WARN([Unable to create Markdown documentation.])])],
      [HAVE_PANDOC=no])
AC_SUBST([HAVE_PANDOC],[$HAVE_PANDOC])
AM_CONDITIONAL([HAVE_PANDOC],[test "x$HAVE_PANDOC" = xyes])


dnl     ============================================================
dnl     8.4. Do we have doctoc installed?
dnl     ============================================================
AS_IF([test "x$enable_documentation" = xyes],
      [AC_CHECK_PROG([DOCTOC],[doctoc],[doctoc])
       AS_IF([test "x$DOCTOC" != x],
             [HAVE_DOCTOC=yes],
             [HAVE_DOCTOC=no
              AC_MSG_WARN([Unable to generate Markdown table of contents.])])],
      [HAVE_DOCTOC=no])
AC_SUBST([HAVE_DOCTOC],[$HAVE_DOCTOC])
AM_CONDITIONAL([HAVE_DOCTOC],[test "x$HAVE_DOCTOC" = xyes])


dnl     ============================================================
dnl     8.5. Should xdg-open be used to open PDFs?
dnl     ============================================================
AC_CHECK_PROG([XDG_OPEN],[xdg-open],[xdg-open])
AS_IF([test "x$XDG_OPEN" != x],
      [HAVE_XDG_OPEN=yes],
      [HAVE_XDG_OPEN=no
       AC_MSG_WARN([PDF opening feature disabled.])])
AC_SUBST([HAVE_XDG_OPEN],[$HAVE_XDG_OPEN])
AM_CONDITIONAL([HAVE_XDG_OPEN],[test "x$HAVE_XDG_OPEN" = xyes])


dnl     ============================================================
dnl     8.6. Should emacsclient be used for editing files?
dnl     ============================================================
AC_CHECK_PROG([EMACSCLIENT],[emacsclient],[emacsclient])
AS_IF([test "x$EMACSCLIENT" != x],
      [HAVE_EMACSCLIENT=yes],
      [HAVE_EMACSCLIENT=no
       AC_MSG_WARN([Text editor launching features disabled.])])
AC_SUBST([HAVE_EMACSCLIENT],[$HAVE_EMACSCLIENT])
AM_CONDITIONAL([HAVE_EMACSCLIENT],[test "x$HAVE_EMACSCLIENT" = xyes])


dnl ================================================================
dnl 9. Git
dnl ================================================================


dnl     ============================================================
dnl     9.1. Are we installing the project git hooks?
dnl     ============================================================
AC_ARG_ENABLE(git_hooks,
              [AS_HELP_STRING([--enable-git-hooks],
                              [Install project git hooks (default: yes)])],,
              [enable_git_hooks=yes])
AC_SUBST([ENABLE_GIT_HOOKS], ["$enable_git_hooks"])
AM_CONDITIONAL(ENABLE_GIT_HOOKS,[test "x$enable_git_hooks" = xyes])


dnl ================================================================
dnl 10. Generate files
dnl ================================================================
AC_OUTPUT([
	Makefile
	Documentation/Makefile
	Documentation/design/Makefile
	Documentation/design/d1/Makefile
	Documentation/design/d1/pages/Makefile
	Documentation/design/d1/pages/renders/Makefile
	Documentation/design/d1/previous/Makefile
	Documentation/design/d1/previous/renders/Makefile
	Documentation/design/d1/tasks/Makefile
	Documentation/design/d1/tasks/renders/Makefile
	Documentation/plan/Makefile
	scripts/Makefile
	scripts/git/Makefile
	scripts/git/hooks/Makefile
	scripts/git/hooks/pre-commit
	tools/Makefile
	tools/dsa.sh
	resources/Makefile
	resources/css/Makefile
	resources/fonts/Makefile
	resources/img/Makefile
	resources/js/Makefile
])


dnl     ============================================================
dnl     10.1. Generate a config summary document
dnl     ============================================================
echo "creating config.summary"

s=config.summary

echo "This file contains a summary of the configuration options generated" > $s
echo "by the configure script, generated at $(date)." >> $s

echo "" >> $s
echo "pip-db - $PIPDB_VERSION" >> $s
echo "--------------" >> $s


# Resources:
echo "" >> $s
echo " • Resources:" >> $s
echo "        Minify CSS:                     $enable_minify_css" >> $s
echo "        Minify JS:                      $enable_minify_js" >> $s


# Extras:
echo "" >> $s
echo " • Extras:" >> $s
echo "        Build tools:                    $enable_tools" >> $s
echo "        Build LaTeX documentation:      $HAVE_PDFLATEX" >> $s
echo "        Build Markdown documentation:   $HAVE_PANDOC" >> $s
echo "        Build Markdown contents pages:  $HAVE_DOCTOC" >> $s
echo "        Install custom git hooks:       $enable_git_hooks" >> $s


dnl     ============================================================
dnl     10.2 Dah dah!
dnl     ============================================================
cat $s | tail -n+3

echo ""
echo "You can now run \`make'."
