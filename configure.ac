AC_PREREQ(2.68)

dnl ================================================================
dnl Project versions (i.e. the package version)
dnl ================================================================
m4_define([pipdb_major_version],[0])
m4_define([pipdb_minor_version],[0])
m4_define([pipdb_micro_version],[1])
m4_define([pipdb_version],[pipdb_major_version.pipdb_minor_version.pipdb_micro_version])


dnl ================================================================
dnl Project details (i.e. the "meta" stuff)
dnl ================================================================
m4_define([author], [chrisc.101@gmail.com])

AC_INIT([pip-db], [pipdb_version], [author])
AC_CONFIG_MACRO_DIR([build])
AC_CONFIG_AUX_DIR([build])


dnl ================================================================
dnl Required versions for dependencies
dnl ================================================================

dnl TODO: Add dependency versions, examples:
dnl m4_define([glib_req_version],           [2.32.0])
dnl m4_define([pangocairo_req_version],     [1.20])

dnl These variables get copied into the generated README
dnl TODO: Add AC_SUBST for dependencies, examples:
dnl AC_SUBST([GLIB_REQ_VERSION], [glib_req_version])
dnl AC_SUBST([GDK_PIXBUF_REQ_VERSION], [gdk_pixbuf_req_version])

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
if test "x$LESSC" != x; then
   HAVE_LESSC=yes
else
   HAVE_LESSC=no
   AC_MSG_ERROR([Less CSS compiler not found.])
fi
AM_CONDITIONAL([HAVE_LESSC],[test "x$HAVE_LESSC" = xyes])


dnl     ============================================================
dnl     Do we have a Java runtime environment?
dnl     ============================================================
AC_CHECK_PROG([JAVA],[java],[java])
if test "x$JAVA" != x; then
   HAVE_JAVA=yes
else
   HAVE_JAVA=no
   AC_MSG_ERROR([No Java virtual machine found.])
fi
AM_CONDITIONAL([HAVE_JAVA],[test "x$HAVE_JAVA" = xyes])


dnl ================================================================
dnl Handle extra configure options
dnl ================================================================


dnl     ============================================================
dnl     Are we building the extra tools?
dnl     ============================================================
AC_ARG_ENABLE(extra_tools,
              AS_HELP_STRING([--enable-extra-tools],
                             [Build extra tools (default: yes)]),,
              [enable_extra_tools=yes])
AM_CONDITIONAL(ENABLE_EXTRA_TOOLS,[test "x$enable_extra_tools" = xyes])


dnl     ============================================================
dnl     Are we using content hashing?
dnl     ============================================================
AC_ARG_ENABLE(content_hashing,
              AS_HELP_STRING([--enable-content-hashing],
                             [Enable cache busting content hashing (default: yes)]),,
              [enable_content_hashing=yes])
AM_CONDITIONAL(ENABLE_CONTENT_HASHING,[test "x$enable_content_hashing" = xyes])


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
dnl     Are we exporting to a local webserver?
dnl     ============================================================
AC_ARG_ENABLE(httpd,
              AS_HELP_STRING([--enable-httpd],
                             [Allow exporting to local webserver (default: yes)]),,
              [enable_httpd=yes])
AM_CONDITIONAL(ENABLE_HTTPD,[test "x$enable_httpd" = xyes])

dnl     ============================================================
dnl     Are we using content hashing?
dnl     ============================================================
HASH=md5sum
HASH_LENGTH=8

AC_CHECK_PROG([HASH_BIN],[$HASH],[$HASH])
if test "x$HASH_BIN" != x; then
   HAVE_HASH_BIN=yes
else
   HAVE_HASH_BIN=no
   AC_MSG_ERROR([$HASH program not found.])
fi
AM_CONDITIONAL([HAVE_HASH_BIN],[test "x$HAVE_HASH_BIN" = xyes])

AC_MSG_CHECKING([length of generated hashes])
hash_test_file=/tmp/$(date +%s)
touch $hash_test_file
GEN_HASH_LENGTH=$($HASH_BIN $hash_test_file | awk '{print $1}' | wc -c)
GEN_HASH_LENGTH=$((GEN_HASH_LENGTH-1))
rm -f $hash_test_file
unset hash_test_file

if test $HASH_LENGTH -ge $GEN_HASH_LENGTH; then
   HASH_LENGTH=$GEN_HASH_LENGTH
   AC_MSG_RESULT([truncated])
   AC_MSG_WARN([Limiting content hash length to $GEN_HASH_LENGTH])
else
   AC_MSG_RESULT([ok])
fi
AC_SUBST([HASH_LENGTH])


dnl ================================================================
dnl Check for dependency packages
dnl ================================================================


dnl     ============================================================
dnl     Should xdg-open be used to open PDFs?
dnl     ============================================================
AC_CHECK_PROG([XDG_OPEN],[xdg-open],[xdg-open])
if test "x$XDG_OPEN" != x; then
   HAVE_XDG_OPEN=yes
else
   HAVE_XDG_OPEN=no
   AC_MSG_WARN([PDF opening feature disabled.])
fi
AM_CONDITIONAL([HAVE_XDG_OPEN],[test "x$HAVE_XDG_OPEN" = xyes])


dnl     ============================================================
dnl     Should emacsclient by used for editing files?
dnl     ============================================================
AC_CHECK_PROG([EMACSCLIENT],[emacsclient],[emacsclient])
if test "x$EMACSCLIENT" != x; then
   HAVE_EMACSCLIENT=yes
else
   HAVE_EMACSCLIENT=no
   AC_MSG_WARN([Text editor launching features disabled.])
fi
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
              [enable_extra_documentation=no])
AM_CONDITIONAL(ENABLE_EXTRA_DOCUMENTATION,[test "x$enable_extra_documentation" = xyes])


dnl     ============================================================
dnl     Do we have pdflatex installed?
dnl     ============================================================
AS_IF([test "x$enable_extra_documentation" = xyes],
      AC_CHECK_PROG([PDFLATEX],[pdflatex],[pdflatex])
      if test "x$PDFLATEX" != x; then
         HAVE_PDFLATEX=yes
      else
         HAVE_PDFLATEX=no
         AC_MSG_WARN([Unable to create PDF documentation.])
      fi,
      HAVE_PDFLATEX=no)
AM_CONDITIONAL([HAVE_PDFLATEX],[test "x$HAVE_PDFLATEX" = xyes])


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
AS_IF([test "x$enable_httpd" = xyes],
      AC_CHECK_FILE([$HTTPD],
                    [HAVE_HTTPD=yes
                     AC_SUBST([HTTPD])],
                    [HAVE_HTTPD=no
                     AC_MSG_WARN([No apache server found.])]),
      [HAVE_HTTPD=no])
AM_CONDITIONAL([HAVE_HTTPD],[test "x$HAVE_HTTPD" = xyes])


dnl     ============================================================
dnl     Do we have a local web-server root?
dnl     ============================================================
WWW_ROOT="/var/www"
AS_IF([test "x$enable_httpd" = xyes],
      AC_CHECK_FILE([$WWW_ROOT],
                    [HAVE_WWW_ROOT=yes
                     AC_SUBST([WWW_ROOT])],
                    [HAVE_WWW_ROOT=no
                     AC_MSG_WARN([Local server root "$WWW_ROOT" not found.])]),
      [HAVE_WWW_ROOT=no])
AM_CONDITIONAL([HAVE_WWW_ROOT],[test "x$HAVE_WWW_ROOT" = xyes])


dnl     ============================================================
dnl     Do we have mysql?
dnl     ============================================================
AS_IF([test "x$enable_httpd" = xyes],
      AC_CHECK_PROG([MYSQL],[mysql],[mysql])
      if test "x$MYSQL" != x; then
         HAVE_MYSQL=yes
      else
         HAVE_MYSQL=no
         AC_MSG_WARN([No mysql database application found])
      fi,
      [HAVE_MYSQL=no])
AM_CONDITIONAL([HAVE_MYSQL],[test "x$HAVE_MYSQL" = xyes])


dnl     ============================================================
dnl     Do we have npm?
dnl     ============================================================
AC_CHECK_PROG([NPM],[npm],[npm])
if test "x$NPM" != x; then
   HAVE_NPM=yes
else
   HAVE_NPM=no
   AC_MSG_ERROR([Node package manager not found.])
fi
AM_CONDITIONAL([HAVE_NPM],[test "x$HAVE_NPM" = xyes])


dnl     ============================================================
dnl     Do we have node?
dnl     ============================================================
dnl TODO: Check for correct version
AC_CHECK_PROG([NODE],[node],[node])
if test "x$NODE" != x; then
   HAVE_NODE=yes
else
   HAVE_NODE=no
   AC_MSG_ERROR([Node.js not found.])
fi
AM_CONDITIONAL([HAVE_NODE],[test "x$HAVE_NODE" = xyes])


dnl         ========================================================
dnl         Do we have the optimist package?
dnl         ========================================================
AC_MSG_CHECKING([for optimist package])
if test -n "$(npm ls -g | grep optimist)"; then
   HAVE_NODE_OPTIMIST=yes
else
   HAVE_NODE_OPTIMIST=no
fi
AC_MSG_RESULT([$HAVE_NODE_OPTIMIST])

AS_IF([test "x$HAVE_NODE_OPTIMIST" = xyes], ,
      [AC_MSG_ERROR([Required node pacakge 'optimist' not found.])])
AM_CONDITIONAL([HAVE_NODE_OPTIMIST],[test "x$HAVE_NODE_OPTIMIST" = xyes])


dnl ================================================================
dnl What needs to be substituted in other files
dnl ================================================================


dnl     ============================================================
dnl     JAR file locations
dnl     ============================================================
m4_define([jar_dir],  ["$(pwd)/build/jar"])

m4_define([html_jar], [jar_dir/htmlcompressor.jar])
m4_define([css_jar],  [jar_dir/yuicompressor.jar])
m4_define([js_jar],   [jar_dir/closure-compiler.jar])

AC_SUBST([HTML_JAR], [html_jar])
AC_SUBST([CSS_JAR],  [css_jar])
AC_SUBST([JS_JAR],   [js_jar])


AC_OUTPUT([
	build/jar/Makefile
	build/Makefile
	design/d1/Makefile
	design/d1/pages/Makefile
	design/d1/pages/renders/Makefile
	design/d1/previous/Makefile
	design/d1/previous/renders/Makefile
	design/d1/tasks/Makefile
	design/d1/tasks/renders/Makefile
	design/Makefile
	Documentation/log/Makefile
	Documentation/Makefile
	Documentation/plan/Makefile
	Makefile
	tools/dsa.sh
	tools/Makefile
	www/css/Makefile
	www/fonts/Makefile
	www/img/Makefile
	www/js/Makefile
	www/Makefile
])


dnl ================================================================
dnl Dah Da!
dnl ================================================================
echo ""
echo "pip-db - $PIPDB_VERSION"
echo "--------------"

echo ""
# Global flags:
echo " • Global:"
if test "x$HAVE_HTTPD" = xyes; then
           echo "        httpd:                          $HTTPD"
           echo "        httpd root:                     $WWW_ROOT/"
else
           echo "        httpd:                          no"
fi
if test "x$HAVE_MYSQL" = xyes; then
           echo "        mysql:                          $MYSQL"
else
           echo "        mysql:                          no"
fi

echo ""
# Features:
echo " • Features:"
echo "        Content hashing enabled:        $enable_content_hashing"
if test "x$enable_content_hashing" = xyes; then
   echo "        Content hashing algorithm:      $HASH"
   echo "        Content hashes length:          $HASH_LENGTH"
fi
echo "        Minify HTML:                    $enable_minify_html"
echo "        Minify CSS:                     $enable_minify_css"
echo "        Minify JS:                      $enable_minify_js"

echo ""
# Build options:
echo " • Build options:"
echo "        Build directory:                $WWW_BUILD/"

echo ""
# Miscellaneous:
echo " • Extra:"
echo "        Build extra tools:              $enable_extra_tools"
echo "        Building extra documentation:   $HAVE_PDFLATEX"

echo ""
