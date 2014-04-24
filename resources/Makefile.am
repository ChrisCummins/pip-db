IMG_REGEXP = \.(bmp|gif|jpg|png|ico)$

DEST = $(PUBLIC_RESOURCES)
SRC = $(shell ls | grep -E '$(IMG_REGEXP)')
IMG = $(addprefix $(DEST)/,$(SRC))

noinst_SCRIPTS = $(IMG)

CLEANFILES = $(find public/)

SUBDIRS = \
	css \
	fonts \
	img \
	js \
	$(NULL)

# Export images
$(DEST)/%: %
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  IMAGE  $(notdir $@)'
	@cat $< > $@ &
