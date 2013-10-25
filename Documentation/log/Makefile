# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))

# The document name
docname = log

# The compilation target
target = $(docname).pdf

# The document sources
src = $(shell find . -name '*.tex')

# Compiled and generated files to remove
clean-files =				\
	$(target)			\
	*.aux 				\
	*.log 				\
	*.log 				\
	*.out 				\
	*.toc 				\
	.\#* 				\
	\#* 				\
	$(NULL)

# Text editor
editor := emacsclient -t

export-dirs = '/mnt/vm/f/chris/Dropbox/Aston University/5 - Final Year/EE4FYP - Final Year Project'

# Compile command
COMPILE = pdftex -output-format pdf -progname pdflatex \
	-file-line-error -interaction errorstopmode

# Default target
all $(target): $(src)
	$(QUIET)$(COMPILE) $(docname).tex
	$(QUIET)$(COMPILE) $(docname).tex
	$(QUIET)for d in $(export-dirs); do		\
	 	if [ -d "$$d" ]; then			\
	 		cp -v $(target) "$$d";		\
	 	fi					\
	done

open: $(target)
	$(QUIET)xdg-open $(target) >/dev/null 2>&1 &

edit:
	$(QUIET)$(editor) `date +%y%m`.tex

distclean clean:
	$(QUIET)rm -vf $(clean-files)

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - compile document'
	@echo '  make open         - open compiled document'
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove compiled document'
	@echo '  make distclean    - remove all generated files'
	@echo '  make edit         - open log file for editing'
	@echo '  make help         - display this help text'
	@echo ''
