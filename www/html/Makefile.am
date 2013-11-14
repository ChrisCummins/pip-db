# Generate lists of source files and targets
HTML_DEST = $(WWW_BUILD)/html
HTML_SRC = $(shell find . -maxdepth 1 -name '*.html' -exec basename {} \;)
HTML = $(addprefix $(HTML_DEST)/,$(HTML_SRC))

noinst_SCRIPTS = $(HTML)

CLEANFILES = $(shell find $(WWW_BUILD) -type f)

EXTRA_DIST = $(HTML_SRC) README.md

# HTML compilation
$(HTML_DEST)/%.html: %.html
	@test -d $(HTML_DEST) || { \
		$(MKDIR_P) $(HTML_DEST); \
	}
	@echo '  HTML   $(notdir $@)'
	@cat $< > $@
