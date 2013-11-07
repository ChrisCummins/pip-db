# Generate lists of source files and targets
IMG_DEST = $(WWW_BUILD)/img
IMG_SRC = $(shell find . -name '*.png' -exec basename {} \;)
IMG = $(addprefix $(IMG_DEST)/,$(IMG_SRC))

noinst_SCRIPTS = $(IMG)

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = $(IMG_SRC)

# Export images
$(IMG_DEST)/% : %
	@test -d $(IMG_DEST) || { \
		$(MKDIR_P) $(IMG_DEST); \
	}
	@echo '  IMAGE  $<'
	@cp $< $@
