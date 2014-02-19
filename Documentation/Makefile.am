SUBDIRS =
	design \
	plan \
	$(NULL)

EXTRA_DIST = \
	Log.md \
	$(NULL)

HTML_DOCS = code.html

doc_DATA = $(HTML_DOCS)
CLEANFILES = $(doc_DATA)

# We exclude Clojure sources which begin with a '.*'
CLOJURE_SRC = $(shell find ../src -type f \( -iname "*.clj" ! -iname ".*" \))

$(HTML_DOCS): $(CLOJURE_SRC)
	@echo '  DOCS   $(HTML_DOCS)'
	@lein marg --dir ./Documentation -f $(HTML_DOCS) >/dev/null 2>&1 || \
	 lein marg --dir ./Documentation -f $(HTML_DOCS)
