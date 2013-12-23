DEST = $(WWW_BUILD)/lib
SRC = $(shell find . -maxdepth 1 -name '*.html' -exec basename {} \;) \
	$(shell find . -maxdepth 1 -name '*.php' -exec basename {} \;)
LIB = $(addprefix $(DEST)/,$(SRC) build-summary.php)
export LIB

SUBDIRS = \
	Twig \
	$(NULL)

noinst_SCRIPTS = $(LIB)

CLEANFILES = build-summary.php

EXTRA_DIST = $(SRC)

.PHONY: build-summary.php

build-summary.php:
	@echo '  EXEC   mkbuild-summary.sh'
	@bash $(top_srcdir)/scripts/mkbuild-summary.sh >/dev/null

$(DEST)/%.php: %.php
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  PHP    $(notdir $@)'
	@cat $< > $@
