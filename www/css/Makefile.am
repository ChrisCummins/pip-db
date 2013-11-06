# Less CSS source files@
LESS = \
	styles.less \
	$(NULL)

# Generate lists of source files and targets
CSS_DEST = $(WWW_BUILD)/css
CSS_SRC = $(shell find . -name '*.css' -exec basename {} \;)
CSS = $(addprefix $(CSS_DEST)/,$(CSS_SRC))

EXTRA_DIST = $(CSS_SRC) $(shell find . -name '*.less' -exec basename {} \;)

CSS_SRC += $(LESS:.less=.css) # Generated CSS

noinst_SCRIPTS = $(CSS) $(LESS:.less=.css)

CLEANFILES = $(shell find $(CSS_DEST) -name '*.css') $(LESS:.less=.css)

# Less CSS compilation
%.css: %.less $(wildcard *.less)
	lessc $< $@

# Compilation command
if ENABLE_MINIFY_CSS
define compile
	$(JAVA) -jar $(CSS_JAR) --charset utf-8 -v --type css $1 > $2
endef
else # Disabled minification
define compile
	cat $1 > $2
endef
endif

# CSS compilation
$(CSS_DEST)/%.css: %.css
	@test -d $(CSS_DEST) || { \
		$(MKDIR_P) $(CSS_DEST); \
	}
if ENABLE_CONTENT_HASHING
	$(eval CHASH := $(shell $(HASH_BIN) $< | cut -c1-$(HASH_LENGTH)))
	$(eval NAME := $(addsuffix -$(CHASH).css,$(patsubst %.css,%,$@)))
else
	$(eval NAME := $@)
endif
	@test -f $(NAME) || { \
		echo '$(call compile,$<,$(NAME))'; \
		$(call compile,$<,$(NAME)); \
	}
if ENABLE_CONTENT_HASHING
	$(eval TARGET := css\/$(patsubst %.css,%,$<))
	@for h in $(HTML); do \
		echo "Updating refs to `basename $(NAME)` in `basename $$h`"; \
		sed -ri 's/(href=\")$(TARGET)(-[0-9a-f]{$(HASH_LENGTH)})?(\.css\")/\1$(TARGET)-$(CHASH)\3/' $$h; \
	done
endif
