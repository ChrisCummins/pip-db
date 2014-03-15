# Our target document:
DOC = report

DOCPDF = $(DOC).pdf
DOCTEX = $(DOC).tex
DOCSRC = $(shell find . -name '*.tex')

# Only create PDF we have the compiler
if HAVE_PDFLATEX
doc_DATA = $(DOCPDF)
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
	proposal.docx \
	proposal.pdf \
	README.md \
	$(NULL)

PDFLATEX_ARGS = -output-format pdf \
	-progname pdflatex \
	-file-line-error \
	-interaction=nonstopmode

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

# If we have xdg-open then we can open the PDF
if HAVE_XDG_OPEN
open:
	@test -f $(DOCPDF) || echo "File '$(DOCPDF)' not found, is it compiled?"
	@test ! -f $(DOCPDF) || $(XDG_OPEN) $(DOCPDF) >/dev/null 2>&1 &
endif
