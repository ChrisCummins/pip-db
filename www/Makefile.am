# Generate lists of source files and targets
SRC = $(shell find . -maxdepth 1 -name '*.html' -exec basename {} \;) \
	$(shell find . -maxdepth 1 -name '*.php' -exec basename {} \;)

TEMPLATE_DEST = $(WWW_BUILD)/html
TEMPLATE_SRC = $(shell find html -maxdepth 1 -name '*.html' -exec basename {} \;)

HTML = $(addprefix $(WWW_BUILD)/,$(SRC)) \
	$(addprefix $(TEMPLATE_DEST)/,$(TEMPLATE_SRC))
export HTML

# We must build this directory first in order to allow for content hashing ref
# updating of HTML build files.
SUBDIRS = \
	. \
	css \
	fonts \
	img \
	js \
	admin \
	lib \
	html \
	$(NULL)

noinst_SCRIPTS = $(HTML) $(WWW_BUILD)/.htaccess

CLEANFILES = $(shell find $(WWW_BUILD) -type f)

EXTRA_DIST = \
	$(SRC) \
	.htaccess.in \
	README.md \
	$(NULL)

# Compilation command
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

# HTML compilation
$(WWW_BUILD)/%.html: %.html
	@test -d $(WWW_BUILD) || { \
		$(MKDIR_P) $(WWW_BUILD); \
	}
	@echo '  HTML   $(notdir $@)'
	@$(call compile,$<,$@)

# PHP compilation
$(WWW_BUILD)/%.php: %.php
	@test -d $(WWW_BUILD) || { \
		$(MKDIR_P) $(WWW_BUILD); \
	}
	@echo '  PHP    $(notdir $@)'
	@cat $< > $@

$(WWW_BUILD)/.htaccess: .htaccess
	@test -d $(WWW_BUILD) || { \
		$(MKDIR_P) $(WWW_BUILD); \
	}
	@echo '  CP     $(notdir $@)'
	@cat $< > $@

if HAVE_WWW_ROOT
# Export build to local webserver
local:
	rsync -avh --delete "$(WWW_BUILD)/" "$(WWW_ROOT)/"
endif
