AC_PREREQ(2.65)

dnl ================================================================
dnl     Autoconf script for pip-db.
dnl
dnl To rebuild the configure script from this, execute command
dnl     autoconf
dnl in the directory containing this script.
dnl ================================================================


dnl ================================================================
dnl Project versions (i.e. the package version)
dnl ================================================================
m4_define([pipdb_major_version],[0])
m4_define([pipdb_minor_version],[0])
m4_define([pipdb_micro_version],[6])
m4_define([pipdb_version],[pipdb_major_version.pipdb_minor_version.pipdb_micro_version])


dnl ================================================================
dnl Project details (i.e. the "meta" stuff)
dnl ================================================================
m4_define([author],[chrisc.101@gmail.com])

AC_INIT([pip-db],[pipdb_version],[author])
AC_CONFIG_SRCDIR([www/index.php])
AC_CONFIG_AUX_DIR([build])

dnl Support for --program-prefix, --program-suffix and
dnl --program-transform-name options
AC_ARG_PROGRAM


dnl Fairly arbitrary, older versions might work too.
AM_INIT_AUTOMAKE([1.11 -Wall foreign no-define])
AM_SILENT_RULES([yes])


dnl ================================================================
dnl Export the package versioning
dnl ================================================================
AC_SUBST([PIPDB_MAJOR_VERSION],[pipdb_major_version])
AC_SUBST([PIPDB_MINOR_VERSION],[pipdb_minor_version])
AC_SUBST([PIPDB_MICRO_VERSION],[pipdb_micro_version])
AC_SUBST([PIPDB_VERSION],[pipdb_version])


dnl ================================================================
dnl Compiler stuff
dnl ================================================================


dnl     ============================================================
dnl     Do we have a Less CSS compiler?
dnl     ============================================================
AC_CHECK_PROG([LESSC],[lessc],[lessc])
AS_IF([test "x$LESSC" != x],
      [HAVE_LESSC=yes],
      [HAVE_LESSC=no
       AC_MSG_ERROR([Less CSS compiler not found.])])
AC_SUBST([HAVE_LESSC],[$HAVE_LESSC])
AM_CONDITIONAL([HAVE_LESSC],[test "x$HAVE_LESSC" = xyes])


dnl     ============================================================
dnl     Do we have a Java runtime environment?
dnl     ============================================================
AC_CHECK_PROG([JAVA],[java],[java])
AS_IF([test "x$JAVA" != x],
      [HAVE_JAVA=yes],
      [HAVE_JAVA=no
       AC_MSG_ERROR([No Java virtual machine found.])])
AC_SUBST([HAVE_JAVA],[$HAVE_JAVA])
AM_CONDITIONAL([HAVE_JAVA],[test "x$HAVE_JAVA" = xyes])


dnl ================================================================
dnl Handle extra configure options
dnl ================================================================


dnl     ============================================================
dnl     Are we installing the built website?
dnl     ============================================================
AC_ARG_ENABLE(install,
              AS_HELP_STRING([--enable-install],
                             [Install built website (default: yes)]),,
              [enable_install=yes])
AM_CONDITIONAL(ENABLE_INSTALL,[test "x$enable_install" = xyes])


dnl     ============================================================
dnl     Are we building the extra tools?
dnl     ============================================================
AC_ARG_ENABLE(extra_tools,
              AS_HELP_STRING([--enable-extra-tools],
                             [Build extra tools (default: yes)]),,
              [enable_extra_tools=yes])
AM_CONDITIONAL(ENABLE_EXTRA_TOOLS,[test "x$enable_extra_tools" = xyes])


dnl     ============================================================
dnl     Are we minifying HTML?
dnl     ============================================================
AC_ARG_ENABLE(minify_html,
              AS_HELP_STRING([--enable-minify-html],
                             [Minify compiled HTML sources (default: yes)]),,
              [enable_minify_html=yes])
AM_CONDITIONAL(ENABLE_MINIFY_HTML,[test "x$enable_minify_html" = xyes])


dnl     ============================================================
dnl     Are we minifying CSS?
dnl     ============================================================
AC_ARG_ENABLE(minify_css,
              AS_HELP_STRING([--enable-minify-css],
                             [Minify compiled CSS sources (default: yes)]),,
              [enable_minify_css=yes])
AM_CONDITIONAL(ENABLE_MINIFY_CSS,[test "x$enable_minify_css" = xyes])


dnl     ============================================================
dnl     Are we minifying JS?
dnl     ============================================================
AC_ARG_ENABLE(minify_js,
              AS_HELP_STRING([--enable-minify-js],
                             [Minify compiled JS sources (default: yes)]),,
              [enable_minify_js=yes])
AM_CONDITIONAL(ENABLE_MINIFY_JS,[test "x$enable_minify_js" = xyes])


dnl     ============================================================
dnl     Check for Website build destination
dnl     ============================================================
m4_define([www_build],["$(pwd)/build/www"])

AC_CHECK_FILE([www_build],,[mkdir -pv www_build])

AC_SUBST([WWW_BUILD],[www_build])


dnl     ============================================================
dnl     Are we using content hashing?
dnl     ============================================================
AC_ARG_ENABLE(content_hashing,
              AS_HELP_STRING([--enable-content-hashing],
                             [Enable cache busting content hashing (default: yes)]),,
              [enable_content_hashing=yes])
AM_CONDITIONAL(ENABLE_CONTENT_HASHING,[test "x$enable_content_hashing" = xyes])

AC_ARG_VAR([HASH],
           [The content hashing binary (default: md5sum)])

AS_IF([test "x$HASH" = x],
      [HASH=md5sum])

AC_ARG_VAR([HASH_LENGTH],
           [The length of truncated content hashes (default: 8)])

AS_IF([test "x$HASH_LENGTH" = x],
      [HASH_LENGTH=8])

dnl If we have content hashing enabled, then check for the hash program.
AS_IF([test "x$enable_content_hashing" = xyes],
      [AC_CHECK_PROG([HASH_BIN],[$HASH],[$HASH])])

AS_IF([test "x$HASH_BIN" != x],
      [HAVE_HASH_BIN=yes],
      [HAVE_HASH_BIN=no])

dnl Fail with an error if we couldn't find it.
AS_IF([test "x$enable_content_hashing" = xyes &&
       test "x$HAVE_HASH_BIN" = xno],
      [AC_MSG_ERROR([$HASH program not found.])])

AC_SUBST([HAVE_HASH_BIN],[$HAVE_HASH_BIN])
AM_CONDITIONAL([HAVE_HASH_BIN],[test "x$HAVE_HASH_BIN" = xyes])

dnl Check that the length of generated hashes is acceptable
AS_IF([test "x$enable_content_hashing" = xyes],
      [AC_MSG_CHECKING([length of generated hashes])
       hash_test_file=/tmp/$(date +%s)
       touch $hash_test_file
       GEN_HASH_LENGTH=$($HASH_BIN $hash_test_file | awk '{print $1}' | wc -c)
       GEN_HASH_LENGTH=$((GEN_HASH_LENGTH-1))
       rm -f $hash_test_file
       unset hash_test_file

       AS_IF([test $HASH_LENGTH -ge $GEN_HASH_LENGTH],
             [HASH_LENGTH=$GEN_HASH_LENGTH
              AC_MSG_RESULT([truncated])
              AC_MSG_WARN([Limiting content hash length to $GEN_HASH_LENGTH])],
             [AC_MSG_RESULT([ok])])
       AC_SUBST([HASH_LENGTH])])


dnl     ============================================================
dnl     Are we building a debugging site?
dnl     ============================================================
AC_ARG_ENABLE(debug,
              AS_HELP_STRING([--enable-debug],
                             [Enable extra website debugging features (default: no)]),
              [enable_debug=yes],
              [enable_debug=no])
AC_SUBST([ENABLE_DEBUG], ["$enable_debug"])
AM_CONDITIONAL(ENABLE_DEBUG,[test "x$enable_debug" = xyes])


dnl     ============================================================
dnl     Are we installing the project git hooks?
dnl     ============================================================
AC_ARG_ENABLE(git_hooks,
              AS_HELP_STRING([--enable-git-hooks],
                             [Install project git hooks (default: no)]),
              [enable_git_hooks=yes],
              [enable_git_hooks=no])
AC_SUBST([ENABLE_GIT_HOOKS], ["$enable_git_hooks"])
AM_CONDITIONAL(ENABLE_GIT_HOOKS,[test "x$enable_git_hooks" = xyes])


dnl ================================================================
dnl Check for dependency packages
dnl ================================================================


dnl     ============================================================
dnl     Should xdg-open be used to open PDFs?
dnl     ============================================================
AC_CHECK_PROG([XDG_OPEN],[xdg-open],[xdg-open])
AS_IF([test "x$XDG_OPEN" != x],
      [HAVE_XDG_OPEN=yes],
      [HAVE_XDG_OPEN=no
       AC_MSG_WARN([PDF opening feature disabled.])])
AC_SUBST([HAVE_XDG_OPEN],[$HAVE_XDG_OPEN])
AM_CONDITIONAL([HAVE_XDG_OPEN],[test "x$HAVE_XDG_OPEN" = xyes])


dnl     ============================================================
dnl     Should emacsclient by used for editing files?
dnl     ============================================================
AC_CHECK_PROG([EMACSCLIENT],[emacsclient],[emacsclient])
AS_IF([test "x$EMACSCLIENT" != x],
      [HAVE_EMACSCLIENT=yes],
      [HAVE_EMACSCLIENT=no
       AC_MSG_WARN([Text editor launching features disabled.])])
AC_SUBST([HAVE_EMACSCLIENT],[$HAVE_EMACSCLIENT])
AM_CONDITIONAL([HAVE_EMACSCLIENT],[test "x$HAVE_EMACSCLIENT" = xyes])


dnl ================================================================
dnl Documentation stuff
dnl ================================================================


dnl     ============================================================
dnl     Should extra documentation be built?
dnl     ============================================================
AC_ARG_ENABLE(extra_documentation,
              AS_HELP_STRING([--enable-extra-documentation],
                             [Build extra documentation (default: no)]),,
              [enable_extra_documentation=yes])
AM_CONDITIONAL(ENABLE_EXTRA_DOCUMENTATION,[test "x$enable_extra_documentation" = xyes])


dnl     ============================================================
dnl     Do we have pdflatex installed?
dnl     ============================================================
AS_IF([test "x$enable_extra_documentation" = xyes],
      AC_CHECK_PROG([PDFLATEX],[pdflatex],[pdflatex])
      AS_IF([test "x$PDFLATEX" != x],
            [HAVE_PDFLATEX=yes],
            [HAVE_PDFLATEX=no
             AC_MSG_WARN([Unable to create PDF documentation.])]),
      HAVE_PDFLATEX=no)
AC_SUBST([HAVE_PDFLATEX],[$HAVE_PDFLATEX])
AM_CONDITIONAL([HAVE_PDFLATEX],[test "x$HAVE_PDFLATEX" = xyes])


dnl     ============================================================
dnl     Do we have pandoc installed?
dnl     ============================================================
AS_IF([test "x$enable_extra_documentation" = xyes],
      AC_CHECK_PROG([PANDOC],[pandoc],[pandoc])
      AS_IF([test "x$PANDOC" != x],
            [HAVE_PANDOC=yes],
            [HAVE_PANDOC=no
             AC_MSG_WARN([Unable to create Markdown documentation.])]),
      HAVE_PANDOC=no)
AC_SUBST([HAVE_PANDOC],[$HAVE_PANDOC])
AM_CONDITIONAL([HAVE_PANDOC],[test "x$HAVE_PANDOC" = xyes])

dnl     ============================================================
dnl     Do we have doctoc installed?
dnl     ============================================================
AS_IF([test "x$enable_extra_documentation" = xyes],
      AC_CHECK_PROG([DOCTOC],[doctoc],[doctoc])
      AS_IF([test "x$DOCTOC" != x],
            [HAVE_DOCTOC=yes],
            [HAVE_DOCTOC=no
             AC_MSG_WARN([Unable to generate Markdown table of contents.])]),
      HAVE_DOCTOC=no)
AC_SUBST([HAVE_DOCTOC],[$HAVE_DOCTOC])
AM_CONDITIONAL([HAVE_DOCTOC],[test "x$HAVE_DOCTOC" = xyes])


dnl ================================================================
dnl Misc program dependencies.
dnl ================================================================
AC_PROG_AWK
AC_PROG_INSTALL
AC_PROG_MKDIR_P
AC_PROG_SED


dnl     ============================================================
dnl     Do we have a local apache2 server?
dnl     ============================================================
HTTPD="/usr/sbin/apache2"
AS_IF([test "x$enable_install" = xyes],
      AC_CHECK_FILE([$HTTPD],
                    [HAVE_HTTPD=yes
                     AC_SUBST([HTTPD])],
                    [HAVE_HTTPD=no
                     AC_MSG_WARN([No apache server found.])]),
      [HAVE_HTTPD=no])
AC_SUBST([HAVE_HTTPD],[$HAVE_HTTPD])
AM_CONDITIONAL([HAVE_HTTPD],[test "x$HAVE_HTTPD" = xyes])


dnl     ============================================================
dnl     Set the document prefix
dnl     ============================================================
AC_ARG_VAR([WWW_PREFIX],
           [The website document prefix (default: none)])

AS_IF([test "x$WWW_PREFIX" != x],
      [HAVE_WWW_PREFIX=yes],
      [HAVE_WWW_PREFIX=no])

AC_SUBST([HAVE_WWW_PREFIX],[$HAVE_WWW_PREFIX])
AM_CONDITIONAL([HAVE_WWW_PREFIX],[test "x$HAVE_WWW_PREFIX" = xyes])


dnl     ============================================================
dnl     Do we have a local web-server root?
dnl     ============================================================
AC_ARG_VAR([WWW_ROOT],
           [The website root (default: /var/www)])

AS_IF([test "x$WWW_ROOT" = x],
      [WWW_ROOT=/var/www])

AS_IF([test "x$enable_install" = xyes],
      AC_CHECK_FILE([$WWW_ROOT],
                    [HAVE_WWW_ROOT=yes
                     AC_SUBST([WWW_ROOT])],
                    [HAVE_WWW_ROOT=no
                     AC_MSG_WARN([Local server root "$WWW_ROOT" not found.])]),
      [HAVE_WWW_ROOT=no])
AC_SUBST([HAVE_WWW_ROOT],[$HAVE_WWW_ROOT])
AM_CONDITIONAL([HAVE_WWW_ROOT],[test "x$HAVE_WWW_ROOT" = xyes])


dnl     ============================================================
dnl     Do we have mysql?
dnl     ============================================================
AS_IF([test "x$enable_install" = xyes],
      AC_CHECK_PROG([MYSQL],[mysql],[mysql])
      AS_IF([test "x$MYSQL" != x],
            [HAVE_MYSQL=yes],
            [HAVE_MYSQL=no
             AC_MSG_WARN([No mysql database application found])]),
      [HAVE_MYSQL=no])
AC_SUBST([HAVE_MYSQL],[$HAVE_MYSQL])
AM_CONDITIONAL([HAVE_MYSQL],[test "x$HAVE_MYSQL" = xyes])


dnl     ============================================================
dnl     Do we have npm?
dnl     ============================================================
AC_CHECK_PROG([NPM],[npm],[npm])
AS_IF([test "x$NPM" != x],
      [HAVE_NPM=yes],
      [HAVE_NPM=no
       AC_MSG_ERROR([Node package manager not found.])])
AC_SUBST([HAVE_NPM],[$HAVE_NPM])
AM_CONDITIONAL([HAVE_NPM],[test "x$HAVE_NPM" = xyes])


dnl     ============================================================
dnl     Do we have node?
dnl     ============================================================
dnl TODO: Check for correct version
AC_CHECK_PROG([NODE],[node],[node])
AS_IF([test "x$NODE" != x],
      [HAVE_NODE=yes],
      [HAVE_NODE=no
       AC_MSG_ERROR([Node.js not found.])])
AC_SUBST([HAVE_NODE],[$HAVE_NODE])
AM_CONDITIONAL([HAVE_NODE],[test "x$HAVE_NODE" = xyes])


dnl         ========================================================
dnl         Do we have the optimist package?
dnl         ========================================================
AC_MSG_CHECKING([for optimist package])
AS_IF([test -n "$(npm ls -g | grep optimist)"],
      [HAVE_NODE_OPTIMIST=yes],
      [HAVE_NODE_OPTIMIST=no])
AC_MSG_RESULT([$HAVE_NODE_OPTIMIST])

AS_IF([test "x$HAVE_NODE_OPTIMIST" = xyes],,
      [AC_MSG_ERROR([Required node pacakge 'optimist' not found.])])
AC_SUBST([HAVE_NODE_OPTIMIST],[$HAVE_NODE_OPTIMIST])
AM_CONDITIONAL([HAVE_NODE_OPTIMIST],[test "x$HAVE_NODE_OPTIMIST" = xyes])


dnl ================================================================
dnl What needs to be substituted in other files
dnl ================================================================


dnl     ============================================================
dnl     JAR file locations
dnl     ============================================================
m4_define([jar_dir], ["$(pwd)/build/jar"])

m4_define([html_jar],[jar_dir/htmlcompressor.jar])
m4_define([css_jar], [jar_dir/yuicompressor.jar])
m4_define([js_jar],  [jar_dir/closure-compiler.jar])

AC_SUBST([HTML_JAR], [html_jar])
AC_SUBST([CSS_JAR],  [css_jar])
AC_SUBST([JS_JAR],   [js_jar])


AC_OUTPUT([
	Makefile
	build/Makefile
	build/jar/Makefile
	design/Makefile
	design/d1/Makefile
	design/d1/pages/Makefile
	design/d1/pages/renders/Makefile
	design/d1/previous/Makefile
	design/d1/previous/renders/Makefile
	design/d1/tasks/Makefile
	design/d1/tasks/renders/Makefile
	Documentation/Makefile
	Documentation/log/Makefile
	Documentation/plan/Makefile
	scripts/Makefile
	scripts/git/Makefile
	scripts/git/hooks/Makefile
	scripts/git/hooks/pre-commit
	tools/Makefile
	tools/dsa.sh
	www/Makefile
	www/.htaccess
	www/admin/Makefile
	www/css/Makefile
	www/fonts/Makefile
	www/html/Makefile
	www/img/Makefile
	www/js/Makefile
	www/lib/Makefile
	www/lib/Twig/Makefile
])


dnl ================================================================
dnl Dah Da!
dnl ================================================================
echo ""
echo "pip-db - $PIPDB_VERSION"
echo "--------------"


echo ""
echo " • Build options:"
echo "        Build destionation:             $WWW_BUILD/"
echo "        Content hashing enabled:        $enable_content_hashing"
AS_IF([test "x$enable_content_hashing" = xyes],
      [echo "        Content hashing algorithm:      $HASH"
       echo "        Content hashes length:          $HASH_LENGTH"])
echo "        Minify HTML:                    $enable_minify_html"
echo "        Minify CSS:                     $enable_minify_css"
echo "        Minify JS:                      $enable_minify_js"
echo "        Install to webserver:           $enable_install"
echo "        Install destination:            $WWW_ROOT"

AS_IF([test "x$enable_install" = xyes],
      [echo ""
       echo " • Webserver:"
       AS_IF([test "x$HAVE_HTTPD" = xyes],
             [echo "        httpd:                          $HTTPD"],
             [echo "        httpd:                          no"])
       AS_IF([test "x$HAVE_MYSQL" = xyes],
             [echo "        mysql:                          $MYSQL"],
             [echo "        mysql:                          no"])])

echo ""
echo " • Website options:"
echo "        Debugging:                      $enable_debug"
echo "        Document root:                  $WWW_ROOT/"
AS_IF([test "x$HAVE_WWW_PREFIX" = xyes],
      [echo "        Link prefix:                    $WWW_PREFIX"],
      [echo "        Link prefix:                    none"])


echo ""
echo " • Extra:"
echo "        Install custom git hooks:       $enable_git_hooks"
echo "        Build extra tools:              $enable_extra_tools"
echo "        Build LaTeX documentation:      $HAVE_PDFLATEX"
echo "        Build Markdown documentation:   $HAVE_PANDOC"


echo ""
