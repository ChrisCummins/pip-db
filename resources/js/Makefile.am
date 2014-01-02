# Generate lists of source files and targets
DEST = $(PUBLIC_RESOURCES)/js
SRC = $(shell find . -name '*.js' -exec basename {} \;)
JS = $(addprefix $(DEST)/,$(SRC))

EXTERN_SRC = \
	bootstrap-3.0.1.min.js \
	jquery-1.10.2.min.js \
	modernizr-2.7.0.min.js \
	$(NULL)

MINIFIED = $(addprefix $(DEST)/, $(EXTERN_SRC))

noinst_SCRIPTS = $(JS)

EXTRA_DIST = $(SRC)

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
$(DEST)/%.js: %.js
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  JS     $(notdir $@)'
	@$(call compile,$<,$@)


$(MINIFIED) : $(DEST)/%.js: %.js
	@test -d $(DEST) || { \
		$(MKDIR_P) $(DEST); \
	}
	@echo '  JS     $(notdir $@)'
	@cat $< > $@
