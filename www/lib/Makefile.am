LIB_DEST = $(WWW_BUILD)/lib
LIB_SRC = $(shell find . -maxdepth 1 -name '*.html' -exec basename {} \;) \
	$(shell find . -maxdepth 1 -name '*.php' -exec basename {} \;)
LIB = $(addprefix $(LIB_DEST)/,$(LIB_SRC))
export LIB

SUBDIRS = \
	twig \
	$(NULL)

noinst_SCRIPTS = $(LIB)

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = $(LIB_SRC)

$(LIB_DEST)/%.php: %.php
	@test -d $(LIB_DEST) || { \
		$(MKDIR_P) $(LIB_DEST); \
	}
	@echo '  PHP    $(notdir $@)'
	@cat $< > $@
