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
	*-blx.bib \
	*.acn \
	*.acr \
	*.alg \
	*.aux \
	*.bbl \
	*.blg \
	*.dvi \
	*.fdb_latexmk \
	*.glg \
	*.glo \
	*.gls \
	*.idx \
	*.ilg \
	*.ind \
	*.ist \
	*.lof \
	*.log \
	*.lol \
	*.lot \
	*.maf \
	*.mtc \
	*.mtc0 \
	*.nav \
	*.nlo \
	*.out \
	*.pdfsync \
	*.ps \
	*.run.xml \
	*.snm \
	*.synctex.gz \
	*.tdo \
	*.toc \
	*.vrb \
	*.xdy \
	.\#* \
	\#* \
	$(NULL)

PDFLATEX_ARGS = -output-format pdf \
	-progname pdflatex \
	-file-line-error \
	-interaction=nonstopmode

# Import lib/*.sty libraries.
export TEXINPUTS := .:./lib:$(TEXINPUTS)

# Compile commands. We pipe the output to /dev/null to silence it, and
# if there is an error, we re-run the compilation without the pipe so
# as to print the failure messages.
define pdflatex
$(PDFLATEX) $(PDFLATEX_ARGS) $1 >/dev/null 2>&1 || $(PDFLATEX) $(PDFLATEX_ARGS) $1
endef

define bibtex
$(BIBTEX) $1 >/dev/null 2>&1 || $(BIBTEX) $1
endef

# Compile twice to refresh cross-references
$(DOCPDF): $(DOCSRC)
	@echo '  LATEX  $(DOCPDF)'
	@$(call pdflatex,$(DOCTEX))
	@echo '  BIBTEX $(DOC)'
	@$(call bibtex,$(DOC))
	@echo '  LATEX  $(DOCPDF)'
	@$(call pdflatex,$(DOCTEX))

# If we have xdg-open then we can open the PDF
if HAVE_XDG_OPEN
open:
	@test -f $(DOCPDF) || echo "File '$(DOCPDF)' not found, is it compiled?"
	@test ! -f $(DOCPDF) || $(XDG_OPEN) $(DOCPDF) >/dev/null 2>&1 &
endif

# If we have texcount then we can perform word counts
if HAVE_TEXCOUNT
wc:
	@$(TEXCOUNT) $(DOCSRC) | $(SED) '1,/TOTAL COUNT/d'
endif

# If we have checkcites then we can perform citation checks
if HAVE_CHECKCITES
cite:
	@$(CHECKCITES) $(DOC).aux
endif
