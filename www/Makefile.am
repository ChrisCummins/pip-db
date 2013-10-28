# Generate lists of source files and targets
HTML_DEST = $(WWW_BUILD)
HTML_SRC = $(shell find . -name '*.html' -exec basename {} \;)
HTML = $(addprefix $(HTML_DEST)/,$(HTML_SRC))
export HTML

# We must build this directory first in order to allow for content hashing ref
# updating of HTML build files.
SUBDIRS = . css js

noinst_SCRIPTS = $(HTML)

CLEANFILES = $(noinst_SCRIPTS)

# HTML compilation
$(HTML_DEST)/%.html: %.html
	@test -d $(HTML_DEST) || { \
		mkdir -p $(HTML_DEST); \
	}
	$(JAVA) -jar $(HTML_JAR) --compress-js --compress-css -o $@ $<
