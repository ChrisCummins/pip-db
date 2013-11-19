# Generate lists of source files and targets
HTML_DEST = $(WWW_BUILD)/html
HTML_SRC = $(shell find . -maxdepth 1 -name '*.html' -exec basename {} \;)
HTML = $(addprefix $(HTML_DEST)/,$(HTML_SRC))

noinst_SCRIPTS = $(HTML)

CLEANFILES = $(shell find $(WWW_BUILD) -type f)

EXTRA_DIST = $(HTML_SRC) README.md

if ENABLE_MINIFY_HTML
define compile
	$(JAVA) -jar $(HTML_JAR) \
		--compress-js --compress-css -o $2 $1
endef
else # Disabled minification
define compile
	cat $1 > $2
endef
endif

$(HTML_DEST)/%.html: %.html
	@test -d $(HTML_DEST) || { \
		$(MKDIR_P) $(HTML_DEST); \
	}
	@echo '  HTML   $(notdir $@)'
	@$(call compile,$<,$@)
