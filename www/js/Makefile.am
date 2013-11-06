# Generate lists of source files and targets
JS_DEST = $(WWW_BUILD)/js
JS_SRC = $(shell find . -name '*.js' -exec basename {} \;)
JS = $(addprefix $(JS_DEST)/,$(JS_SRC))

noinst_SCRIPTS = $(JS)

CLEANFILES = $(shell find $(JS_DEST) -name '*.js')

EXTRA_DIST = $(JS_SRC)

# Compilation command
if ENABLE_MINIFY_JS
define compile
	$(JAVA) -jar $(JS_JAR) --js=$1 --js_output_file=$2
endef
else # Disabled minification
define compile
	cat $1 > $2
endef
endif

# JavaScript compilation
$(JS_DEST)/%.js: %.js
	@test -d $(JS_DEST) || { \
		$(MKDIR_P) $(JS_DEST); \
	}
	$(eval CHASH := $(shell $(HASH_BIN) $< | cut -c1-$(HASH_LENGTH)))
	$(eval NAME := $(addsuffix -$(CHASH).js,$(patsubst %.js,%,$@)))
	@test -f $(NAME) || { \
		echo '$(call compile,$<,$(NAME))'; \
		$(call compile,$<,$(NAME)); \
	}
	$(eval TARGET := js\/$(patsubst %.js,%,$<))
	@for h in $(HTML); do \
		echo "Updating refs to `basename $(NAME)` in `basename $$h`"; \
		sed -ri 's/(<script src=\")$(TARGET)(-[0-9a-f]{$(HASH_LENGTH)})?(\.js\")/\1$(TARGET)-$(CHASH)\3/' $$h; \
	done
