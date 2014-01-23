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

$(HTML_DOCS): $(shell find ../src -name '*.clj')
	@echo '  DOCS   $(HTML_DOCS)'
	@echo $<
	@lein marg --dir ./Documentation -f $(HTML_DOCS) >/dev/null
