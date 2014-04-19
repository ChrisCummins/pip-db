# Generate lists of source files and targets
DEST = $(PUBLIC_RESOURCES)/fonts
SRC = $(shell find . -exec basename {} \; | tail -n+2 | grep -v Makefile)
FONTS = $(addprefix $(DEST)/,$(SRC))

noinst_SCRIPTS = $(FONTS)

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = $(SRC)

# Forked font exporting
$(DEST)/% : %
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  FONT   $<'
	@cat $< > $@ &
