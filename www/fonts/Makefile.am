# Generate lists of source files and targets
FONTS_DEST = $(WWW_BUILD)/fonts
FONTS_SRC = $(shell find . -exec basename {} \; | tail -n+2 | grep -v Makefile)
FONTS = $(addprefix $(FONTS_DEST)/,$(FONTS_SRC))

noinst_SCRIPTS = $(FONTS)

EXTRA_DIST = $(FONTS_SRC)

# Export fonts
$(FONTS_DEST)/% : %
	@test -d $(FONTS_DEST) || { \
		$(MKDIR_P) $(FONTS_DEST); \
	}
	@echo '  FONT   $<'
	@cp $< $@
