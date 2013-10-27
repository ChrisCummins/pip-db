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

# Export location for www
WWW_ROOT="/var/www"
AC_CHECK_FILE([$WWW_ROOT],
              AC_SUBST([WWW_ROOT]),
	      AC_MSG_WARN([Local server root "$WWW_ROOT" not found.]))
AM_CONDITIONAL([HAVE_WWW_ROOT], test -d "$WWW_ROOT")

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

# Automake
AM_INIT_AUTOMAKE([1.11 -Wall foreign no-define])
AM_SILENT_RULES([yes])

# Autoconf output files
AC_OUTPUT([
	Makefile
	Documentation/Makefile
	Documentation/log/Makefile
	tools/Makefile
])
