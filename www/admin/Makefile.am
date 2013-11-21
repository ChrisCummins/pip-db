DEST = $(WWW_BUILD)/admin
SRC = $(shell find . -maxdepth 1 -name '*.php' -exec basename {} \;)
PHP = $(addprefix $(DEST)/,$(SRC))
export PHP

noinst_SCRIPTS = $(PHP)

EXTRA_DIST = $(SRC)

$(DEST)/%.php: %.php
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  PHP    $(notdir $@)'
	@cat $< > $@
