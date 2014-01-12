PATH := $(LESSC_BIN_DIR):$(NODE_BIN_DIR):${PATH}

# Less CSS source files@
LESS = \
	login.less \
	styles.less \
	$(NULL)

# Generate lists of source files and targets
DEST = $(PUBLIC_RESOURCES)/css
SRC = $(shell find . -name '*.css' -exec basename {} \;)
CSS = $(addprefix $(DEST)/,$(SRC))

EXTRA_DIST = $(SRC) $(shell find . -name '*.less' -exec basename {} \;)

SRC += $(LESS:.less=.css) # Generated CSS

noinst_SCRIPTS = $(CSS) $(LESS:.less=.css)

CLEANFILES = $(LESS:.less=.css) $(noinst_SCRIPTS)

# Less CSS compilation
%.css: %.less $(wildcard *.less)
	@echo '  LESS   $@'
	@lessc $< $@

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
$(DEST)/%.css: %.css
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  CSS    $(notdir $@)'
	@$(call compile,$<,$@)
