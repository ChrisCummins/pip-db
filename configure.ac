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
AC_CHECK_PROG([LESSC], [lessc], [lessc])
if test -z "$LESSC"; then
   AC_MSG_ERROR([Less CSS compiler not found.])
fi
AM_CONDITIONAL([HAVE_LESSC], test -n "$LESSC")


dnl     ============================================================
dnl     Do we have a Java runtime environment?
dnl     ============================================================
AC_CHECK_PROG([JAVA], [java], [java])
if test -z "$JAVA"; then
   AC_MSG_ERROR([No Java virtual machine found.])
fi


dnl ================================================================
dnl Handle extra configure options
dnl ================================================================
dnl TODO: Add subsections for each AC_ARG_ENABLE


dnl     ============================================================
dnl     Are we using content hashing?
dnl     ============================================================
HASH=md5sum   dnl The program used to generate hashes
HASH_LENGTH=8 dnl The length of hashes used for content hashing

AC_CHECK_PROG(HASH_BIN, $HASH, $HASH)
if test -z "$HASH_BIN"; then
   AC_MSG_ERROR([$HASH program is needed.])
fi

dnl Get the length (in characters) of generated hashes
TMP_TEST=/tmp/`date +%s`
touch $TMP_TEST
GEN_HASH_LENGTH=`$HASH_BIN $TMP_TEST | awk '{print $1}' | wc -c`
GEN_HASH_LENGTH=$((GEN_HASH_LENGTH-1))
rm -f "$TMP_TEST"

dnl Limit the length of hashes to GEN_HASH_LENGTH
if test $HASH_LENGTH -ge $GEN_HASH_LENGTH; then
   AC_MSG_WARN([Limiting content hash length to $GEN_HASH_LENGTH])
   HASH_LENGTH=$GEN_HASH_LENGTH
fi
AC_SUBST([HASH_LENGTH])


dnl ================================================================
dnl Check for dependency packages
dnl ================================================================


dnl     ============================================================
dnl     Should LaTeX documentation be built?
dnl     ============================================================
AC_CHECK_PROG(PDFLATEX, pdflatex, pdflatex)
if test -z "$PDFLATEX"; then
   AC_MSG_WARN([Unable to create PDF documentation.])
fi
AM_CONDITIONAL([HAVE_PDFLATEX], test -n "$PDFLATEX")


dnl     ============================================================
dnl     Should xdg-open be used to open PDFs?
dnl     ============================================================
AC_CHECK_PROG([XDG_OPEN], [xdg-open], [xdg-open])
if test -z "$XDG_OPEN"; then
   AC_MSG_WARN([PDF opening feature disabled.])
fi
AM_CONDITIONAL([HAVE_XDG_OPEN], test -n "$XDG_OPEN")


dnl     ============================================================
dnl     Should emacsclient by used for editing files?
dnl     ============================================================
AC_CHECK_PROG([EMACSCLIENT], [emacsclient], [emacsclient])
if test -z "$EMACSCLIENT"; then
   AC_MSG_WARN([Text editor launching features disabled.])
fi
AM_CONDITIONAL([HAVE_EMACSCLIENT], test -n "$EMACSCLIENT")


dnl ================================================================
dnl Documentation stuff
dnl ================================================================
dnl TODO: Add AC_ARG_ENABLE check for extra documentation


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
AC_CHECK_FILE([$HTTPD], , AC_MSG_WARN([No apache server found.]))
AM_CONDITIONAL([HAVE_HTTPD], test -f "$HTTPD")


dnl     ============================================================
dnl     Do we have a local web-server root?
dnl     ============================================================
WWW_ROOT="/var/www"
AC_CHECK_FILE([$WWW_ROOT],
              AC_SUBST([WWW_ROOT]),
	      AC_MSG_WARN([Local server root "$WWW_ROOT" not found.]))
AM_CONDITIONAL([HAVE_WWW_ROOT], test -d "$WWW_ROOT")


dnl     ============================================================
dnl     Do we have mysql?
dnl     ============================================================
AC_CHECK_PROG([MYSQL], [mysql], [mysql])
if test -z "$MYSQL"; then
   AC_MSG_WARN([No mysql database application found])
fi
AM_CONDITIONAL([HAVE_MYSQL], test -n "$MYSQL")


dnl     ============================================================
dnl     Do we have npm?
dnl     ============================================================
AC_CHECK_PROG([NPM], [npm], [npm])
if test -z "$NPM"; then
   AC_MSG_ERROR([Node package manager not found.])
fi
AM_CONDITIONAL([HAVE_NPM], test -n "$NPM")


dnl     ============================================================
dnl     Do we have node?
dnl     ============================================================
dnl TODO: Check for correct version
AC_CHECK_PROG([NODE], [node], [node])
if test -z "$NODE"; then
   AC_MSG_ERROR([Node.js not found.])
fi
AM_CONDITIONAL([HAVE_NODE], test -n "$NODE")


dnl         ========================================================
dnl         Do we have the optimist package?
dnl         ========================================================
echo -n "checking for optimist package... "
TMP=`npm ls -g | grep optimist`
if test -z "$TMP"; then
   echo "no"
   AC_MSG_ERROR([Required node pacakge 'optimist' not found.])
else
   echo "yes"
fi
unset TMP
AM_CONDITIONAL([HAVE_NODE_OPTIMIST], test -n "$TMP")


dnl ================================================================
dnl What needs to be substituted in other files
dnl ================================================================


dnl     ============================================================
dnl     JAR file locations
dnl     ============================================================
JAR_DIR="`pwd`/build/jar"
HTML_JAR="$JAR_DIR/htmlcompressor.jar"
AC_SUBST([HTML_JAR])
CSS_JAR="$JAR_DIR/yuicompressor.jar"
AC_SUBST([CSS_JAR])
JS_JAR="$JAR_DIR/closure-compiler.jar"
AC_SUBST([JS_JAR])


dnl     ============================================================
dnl     Website build destination
dnl     ============================================================
WWW_BUILD="`pwd`/build/www"
AC_SUBST([WWW_BUILD])
AC_CHECK_FILE([$WWW_BUILD],
	      ,
 	      [mkdir -pv "$WWW_BUILD"])


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
dnl TODO: Beautify configure output
echo ""
echo "Summary:"
echo ""
AM_COND_IF([HAVE_HTTPD],
           echo "    httpd:                            $HTTPD";
           echo "    httpd root:                       $WWW_ROOT",
           echo "    httpd:                            no")
echo -n "    Database management system:       "
AM_COND_IF([HAVE_MYSQL], echo "$MYSQL", echo "no")
echo "    Build directory:                  $WWW_BUILD"
echo "    Content hashing algorithm:        $HASH"
echo "    Content hashes length:            $HASH_LENGTH"
echo -n "    Building extra documentation:     "
AM_COND_IF([HAVE_PDFLATEX], echo "yes", echo "no")
echo ""
