# Autoconf
AC_PREREQ(2.68)
AC_INIT([protein-db], [0.0.1], [chrisc.101@gmail.com])
AC_CONFIG_MACRO_DIR([build])
AC_CONFIG_AUX_DIR([build])

# Define INSTALL
AC_PROG_INSTALL

# Check for LaTeX compiler and define PDFLATEX and HAVE_PDFLATEX
AC_CHECK_PROG(PDFLATEX, pdflatex, pdflatex)
if test -z "$PDFLATEX"; then
   AC_MSG_WARN([Unable to create PDF documentation.])
fi
AM_CONDITIONAL([HAVE_PDFLATEX], test -n "$PDFLATEX")

# Check for xdg-open for opening PDF documentation
AC_CHECK_PROG([XDG_OPEN], [xdg-open], [xdg-open])
if test -z "$XDG_OPEN"; then
   AC_MSG_WARN([PDF opening feature disabled.])
fi
AM_CONDITIONAL([HAVE_XDG_OPEN], test -n "$XDG_OPEN")

# Check for emacsclient for editing files
AC_CHECK_PROG([EMACSCLIENT], [emacsclient], [emacsclient])
if test -z "$EMACSCLIENT"; then
   AC_MSG_WARN([Text editor launching features disabled.])
fi
AM_CONDITIONAL([HAVE_EMACSCLIENT], test -n "$EMACSCLIENT")

# Export location for www
WWW_ROOT="/var/www"
AC_CHECK_FILE([$WWW_ROOT],
              AC_SUBST([WWW_ROOT]),
	      AC_MSG_WARN([Local server root "$WWW_ROOT" not found.]))
AM_CONDITIONAL([HAVE_WWW_ROOT], test -d "$WWW_ROOT")

# Check for Apache server
HTTPD="/usr/sbin/apache2"
AC_CHECK_FILE([$HTTPD], , AC_MSG_WARN([No apache server found.]))
AM_CONDITIONAL([HAVE_HTTPD], test -f "$HTTPD")

# Check for MySQL
AC_CHECK_PROG([MYSQL], [mysql], [mysql])
if test -z "$MYSQL"; then
   AC_MSG_WARN([No mysql database application found])
fi
AM_CONDITIONAL([HAVE_MYSQL], test -n "$MYSQL")

# Check for node else fail
AC_CHECK_PROG([NODE], [node], [node])
if test -z "$NODE"; then
   AC_MSG_ERROR([Node.js not found.])
fi
AM_CONDITIONAL([HAVE_NODE], test -n "$NODE")

# Check for npm else fail
AC_CHECK_PROG([NPM], [npm], [npm])
if test -z "$NPM"; then
   AC_MSG_ERROR([Node package manager not found.])
fi
AM_CONDITIONAL([HAVE_NPM], test -n "$NPM")

# Check for awk
AC_PROG_AWK

# Check for sed
AC_PROG_SED

# Check for mkdir -p
AC_PROG_MKDIR_P

# Build location for www
WWW_BUILD="`pwd`/build/www"
AC_SUBST([WWW_BUILD])
AC_CHECK_FILE([$WWW_BUILD],
	      ,
 	      [mkdir -pv "$WWW_BUILD"])

# Check for hash binary
HASH=md5sum
AC_CHECK_PROG(HASH_BIN, $HASH, $HASH)
if test -z "$HASH_BIN"; then
   AC_MSG_ERROR([$HASH program is needed.])
fi

# Length of hashes used for content hashing
HASH_LENGTH=8

# Get the length (in characters) of generated hashes
TMP_TEST=/tmp/`date +%s`
touch $TMP_TEST
GEN_HASH_LENGTH=`$HASH_BIN $TMP_TEST | awk '{print $1}' | wc -c`
GEN_HASH_LENGTH=$((GEN_HASH_LENGTH-1))
rm -f "$TMP_TEST"

# Limit the length of hashes to GEN_HASH_LENGTH
if test $HASH_LENGTH -ge $GEN_HASH_LENGTH; then
   AC_MSG_WARN([Limiting content hash length to $GEN_HASH_LENGTH])
   HASH_LENGTH=$GEN_HASH_LENGTH
fi
AC_SUBST([HASH_LENGTH])

# Check for Java VM
AC_CHECK_PROG([JAVA], [java], [java])
if test -z "$JAVA"; then
   AC_MSG_ERROR([No Java virtual machine found.])
fi

# Compilers and minifiers
JAR_DIR="`pwd`/build/jar"
HTML_JAR="$JAR_DIR/htmlcompressor.jar"
AC_SUBST([HTML_JAR])
CSS_JAR="$JAR_DIR/yuicompressor.jar"
AC_SUBST([CSS_JAR])
JS_JAR="$JAR_DIR/closure-compiler.jar"
AC_SUBST([JS_JAR])

# Automake
AM_INIT_AUTOMAKE([1.11 -Wall foreign no-define])
AM_SILENT_RULES([yes])

# Autoconf output files
AC_OUTPUT([
	Documentation/log/Makefile
	Documentation/Makefile
	Makefile
	tools/dsa.sh
	tools/Makefile
	www/css/Makefile
	www/js/Makefile
	www/Makefile
])

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
