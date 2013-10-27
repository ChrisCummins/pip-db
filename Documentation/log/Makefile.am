# Our target document:
DOC = log

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

# Compile twice to refresh cross-references
$(DOCPDF): $(DOCSRC)
	$(QUIET)$(PDFLATEX) -output-format pdf -progname pdflatex \
		-file-line-error -interaction errorstopmode $(DOCTEX)
	$(QUIET)$(PDFLATEX) -output-format pdf -progname pdflatex \
		-file-line-error -interaction errorstopmode $(DOCTEX)
