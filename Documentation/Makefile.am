SUBDIRS =
	evaluation \
	midterm \
	plan \
	report \
	$(NULL)

HTML_DOCS = code.html

doc_DATA = $(HTML_DOCS)
CLEANFILES = $(doc_DATA)

# We exclude Clojure sources which begin with a '.*'
CLOJURE_SRC = $(shell find ../src -type f \( -iname "*.clj" ! -iname ".*" \))

# Note that the Clojure sources depend on the analytics tracking
# snippet in order to compile. If we're running a parallel make then
# there's no guarantee that the forked analytics process has
# completed, so by calling touch here, we're guaranteeing that the
# file will always exist, even if the contents can't be guaranteed.
$(HTML_DOCS): $(CLOJURE_SRC)
	@echo '  DOCS   $(HTML_DOCS)'
	@(touch ../resources/public/js/google-analytics.inline.js; \
	  lein marg --dir ./Documentation -f $(HTML_DOCS) >/dev/null 2>&1 || \
	  lein marg --dir ./Documentation -f $(HTML_DOCS)) &
