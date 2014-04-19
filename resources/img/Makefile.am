IMG_REGEXP = \.(bmp|gif|jpg|png|ico)$

# Generate lists of source files and targets
DEST = $(PUBLIC_RESOURCES)/img
SRC = $(shell ls | grep -E '$(IMG_REGEXP)')
IMG = $(addprefix $(DEST)/,$(SRC))

noinst_SCRIPTS = $(IMG)

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = $(SRC)

# Export images
$(DEST)/%: %
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  IMAGE  $(notdir $@)'
	@cat $< > $@ &
