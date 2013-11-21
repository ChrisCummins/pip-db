# Generate lists of source files and targets
SRC = $(shell find . -maxdepth 1 -name '*.php' -exec basename {} \;)
PHP = $(addprefix $(WWW_BUILD)/,$(SRC))

TEMPLATE_DEST = $(WWW_BUILD)/html
TEMPLATE_SRC = $(shell find html -maxdepth 1 -name '*.html' -exec basename {} \;)
HTML = $(addprefix $(TEMPLATE_DEST)/,$(TEMPLATE_SRC))
export HTML

# We must build this directory first in order to allow for content hashing ref
# updating of HTML build files.
SUBDIRS = \
	. \
	html \
	css \
	fonts \
	img \
	js \
	admin \
	lib \
	$(NULL)

CLEANFILES = $(shell find $(WWW_BUILD) -type f)

noinst_SCRIPTS = $(PHP) $(WWW_BUILD)/.htaccess

EXTRA_DIST = \
	$(SRC) \
	.htaccess.in \
	README.md \
	$(NULL)

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
	@echo '  APACHE $(notdir $@)'
	@cat $< > $@

if HAVE_WWW_ROOT
# Export build to local webserver
install-www local:
	rsync -avh --delete "$(WWW_BUILD)/" "$(WWW_ROOT)/"

install-data-local: local

clean-www clean-local:
	rm -rvf $(WWW_ROOT)/

endif
