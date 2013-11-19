# Our target document:
DOC = log

DOCPDF = $(DOC).pdf
DOCTEX = $(DOC).tex
DOCSRC = $(shell find . -name '*.tex')

doc_DATA =

if HAVE_PDFLATEX
doc_DATA += $(DOCPDF)
endif

CLEANFILES = \
	$(DOCPDF) \
	*.aux \
	*.log \
	*.log \
	*.out \
	*.toc \
	.\#* \
	\#* \
	$(NULL)

EXTRA_DIST = \
	$(DOCSRC) \
	README.md \
	$(NULL)

PDFLATEX_ARGS = -output-format pdf \
	-progname pdflatex \
	-file-line-error \
	-interaction=nonstopmode

EXPORT_DIR = "$(HOME)/Dropbox/Aston University/5 - Final Year/EE4FYP - Final Year Project/"
EXPORT_NAME = "Project Log.pdf"

# Compile command. We pipe the output to /dev/null to silence it, and if there
# is an error, we re-run the compilation without the pipe so as to print the
# failure messages.
define compile
$(PDFLATEX) $(PDFLATEX_ARGS) $1 >/dev/null || $(PDFLATEX) $(PDFLATEX_ARGS) $1
endef

# Compile twice to refresh cross-references
$(DOCPDF): $(DOCSRC)
	@echo '  LATEX  $(DOCPDF)'
	@$(call compile,$(DOCTEX))
	@$(call compile,$(DOCTEX))

# Export the compiled document
all-am: $(DOCPDF)
	@test -d $(EXPORT_DIR) && { \
		cp $< $(EXPORT_DIR)/$(EXPORT_NAME); \
	}

# If we have xdg-open then we can open the PDF
if HAVE_XDG_OPEN
open:
	@test -f $(DOCPDF) || echo "File '$(DOCPDF)' not found, is it compiled?"
	@test ! -f $(DOCPDF) || $(XDG_OPEN) $(DOCPDF) >/dev/null 2>&1 &
endif

# If we have emacsclient then we can edit the sources
if HAVE_EMACSCLIENT
edit:
	@$(EMACSCLIENT) -t `date +%y%m`.tex
endif
