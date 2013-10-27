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

# Automake
AM_INIT_AUTOMAKE([1.11 -Wall foreign no-define])
AM_SILENT_RULES([yes])

# Autoconf output files
AC_OUTPUT([
	Makefile
	tools/Makefile
])
