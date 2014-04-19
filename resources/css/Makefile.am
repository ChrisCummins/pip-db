PATH := $(LESSC_BIN_DIR):$(NODE_BIN_DIR):${PATH}

# Less CSS source files@
LESS = \
	styles.less \
	$(NULL)

# Generate lists of source files and targets
DEST = $(PUBLIC_RESOURCES)/css
SRC = $(shell find . -name '*.css' -exec basename {} \;)
CSS = $(addprefix $(DEST)/,$(SRC))

EXTRA_DIST = $(SRC) $(shell find . -name '*.less' -exec basename {} \;)

noinst_SCRIPTS = $(CSS) $(addprefix $(DEST)/,$(LESS:.less=.css))

CLEANFILES = $(LESS:.less=.css) $(noinst_SCRIPTS)

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

# Parallel Less CSS compilation
$(DEST)/%.css: %.less $(wildcard *.less)
	$(eval NAME := $(notdir $@))
	@echo '  LESS   $(NAME)'
	@(lessc $< $(NAME); \
	  test -d $(DEST) || { \
		  $(MKDIR_P) $(DEST); \
	  }; \
	  $(call compile,$(NAME),$(DEST)/$(NAME))) &

# Parallel CSS compilation
$(DEST)/%.css: %.css
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  CSS    $(notdir $@)'
	@$(call compile,$<,$@) &
