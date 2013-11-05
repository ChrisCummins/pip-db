# Generate lists of source files and targets
HTML_DEST = $(WWW_BUILD)
HTML_SRC = $(shell find . -name '*.html' -exec basename {} \;) \
	$(shell find . -name '*.php' -exec basename {} \;)
HTML = $(addprefix $(HTML_DEST)/,$(HTML_SRC))
export HTML

# We must build this directory first in order to allow for content hashing ref
# updating of HTML build files.
SUBDIRS = . css js

noinst_SCRIPTS = $(HTML)

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = \
	$(HTML_SRC) \
	README.md \
	$(NULL)

# HTML compilation
$(HTML_DEST)/%.html: %.html
	@test -d $(HTML_DEST) || { \
		$(MKDIR_P) $(HTML_DEST); \
	}
	$(JAVA) -jar $(HTML_JAR) --compress-js --compress-css -o $@ $<

# PHP compilation
$(HTML_DEST)/%.php: %.php
	@test -d $(HTML_DEST) || { \
		$(MKDIR_P) $(HTML_DEST); \
	}
	$(JAVA) -jar $(HTML_JAR) --compress-js --compress-css -o $@ $<

# Export build to local webserver
local:
	@test -d "$(WWW_ROOT)" || { \
		echo -n "fatal: destination directory " >&2; \
		echo -n "'$(WWW_ROOT)'" >&2; \
		echo    " not found!" >&2; \
		echo >&2; \
		exit 2; \
	}
	rsync -avh --delete "$(WWW_BUILD)/" "$(WWW_ROOT)/"
