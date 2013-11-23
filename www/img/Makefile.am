# Generate lists of source files and targets
DEST = $(WWW_BUILD)/img
SRC = $(shell find . -name '*.png' -exec basename {} \;)
IMG = $(addprefix $(DEST)/,$(SRC))

noinst_SCRIPTS = $(IMG)

EXTRA_DIST = $(SRC)

# Export images
$(DEST)/%.png : %.png
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  IMAGE  $<'
	@cp $< $@
