# Our target document:
DOC = log

DOCPDF = $(DOC).pdf
DOCTEX = $(DOC).tex
DOCSRC = $(shell find . -name '*.tex')
DOCMD  = README.md

doc_DATA =

if HAVE_PDFLATEX
doc_DATA += $(DOCPDF)
endif

if HAVE_PANDOC
doc_DATA += $(DOCMD)
endif

CLEANFILES = \
	$(doc_DATA) \
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

$(DOCMD): $(DOCSRC)
	@echo '  MD     $@'
	@$(PANDOC) --biblatex -o $@ $<
if HAVE_DOCTOC
	@$(DOCTOC) $@ > /dev/null
endif


# Export the compiled document
all-am: $(DOCPDF)
	@test -d $(EXPORT_DIR) && { \
		cp $< $(EXPORT_DIR)/$(EXPORT_NAME); \
	}

if HAVE_XDG_OPEN
# Open the PDF log
open:
	@test -f $(DOCPDF) || make $(DOCPDF)
	@test ! -f $(DOCPDF) || $(XDG_OPEN) $(DOCPDF) >/dev/null 2>&1 &
endif

if HAVE_EMACSCLIENT
# Edit the current month's log
edit:
	@$(EMACSCLIENT) -t `date +%y%m`.tex
endif
