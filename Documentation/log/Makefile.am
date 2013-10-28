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

# Compile command
define compile
$(PDFLATEX) -output-format pdf \
	-progname pdflatex \
	-file-line-error \
	-interaction errorstopmode $1
endef

# Compile twice to refresh cross-references
$(DOCPDF): $(DOCSRC)
	$(call compile,$(DOCTEX))
	$(call compile,$(DOCTEX))
