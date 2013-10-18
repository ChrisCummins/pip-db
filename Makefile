#################
# Configuration #
#################

#
# The path to local test server.
#
# When 'make local' is executed, the contents of this directory are overwritten
# by the build files located in $(BUILDIR).
#
LOCAL_SERVER_ROOT := /var/www

# The number of truncated hash characters to include in file name
hash-length = 16

#########################
# Files and Directories #
#########################

#
# The source and build directories.
#
# These are the prefixes for source and destination directories. The source
# files found within $(SRC_DIR) are compiled and exported to $(BUILDIR).
#
SRC_DIR := src
BUILDIR := build

#
# The subdirectories of $(SRC_DIR) which contain the different types of source
# files. At build time, these directories will be scanned and the found files
# will be compiled and exported to their matching subdirectories in $(BUILDIR).
#
HTML_DIR := .
CSS_DIR  := css
JS_DIR   := js

################
# Source files #
################

#
# Less CSS source files.
#
# List here all Less CSS files to be compiled into CSS files. Less sources must
# be in the $(SRC_DIR)/$(CSS_DIR) directory.
#
LESS_BASENAMES = styles.less

## END OF USER CONFIGURABLE VARIABLES.

######################
# Internal variables #
######################

# Derived file paths
HTML_SRC  = $(SRC_DIR)/$(HTML_DIR)
HTML_DEST = $(BUILDIR)/$(HTML_DIR)

CSS_SRC   = $(SRC_DIR)/$(CSS_DIR)
CSS_DEST  = $(BUILDIR)/$(CSS_DIR)

JS_SRC    = $(SRC_DIR)/$(JS_DIR)
JS_DEST   = $(BUILDIR)/$(JS_DIR)

# Generated lists of source files
HTML              = $(shell find $(HTML_SRC) -name '*.html' -exec basename {} \;)
HTML_SOURCE_FILES = $(addprefix $(HTML_SRC)/,$(HTML))
HTML_BUILD_FILES  = $(addprefix $(HTML_DEST)/,$(HTML))

CSS_BASENAMES     = $(shell find $(CSS_SRC) -name '*.css' -exec basename {} \;)
CSS_SOURCE_FILES  = $(addprefix $(CSS_SRC)/,$(CSS_BASENAMES))
CSS_BUILD_FILES   = $(addprefix $(CSS_DEST)/,$(CSS_BASENAMES))

LESS_TARGETS      = $(LESS_BASENAMES:.less=.css)
LESS_BUILD_FILES  = $(addprefix $(CSS_SRC)/,$(LESS_TARGETS))
CSS_BASENAMES    += $(LESS_TARGETS) # Append less targets to CSS sources list

JS_BASENAMES      = $(shell find $(JS_SRC) -name '*.js' -exec basename {} \;)
JS_SOURCE_FILES   = $(addprefix $(JS_SRC)/,$(JS_BASENAMES))
JS_BUILD_FILES    = $(addprefix $(JS_DEST)/,$(JS_BASENAMES))

# Full site contents
SITE =									\
	$(HTML_BUILD_FILES)						\
	$(LESS_BUILD_FILES)						\
	$(CSS_BUILD_FILES)						\
	$(JS_BUILD_FILES)						\
	$(NULL)

##
## Verbosity:
##

# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))

# Define $(v) variable for -v flag
ifdef V
	v = -v
endif

compilers = lib/build
html-compiler = $(compilers)/htmlcompressor.jar
css-compiler = $(compilers)/yuicompressor.jar
js-compiler = $(compilers)/closure-compiler.jar

##
## Compiling
##

all: $(SITE)

$(HTML_DEST)/%.html: $(HTML_SRC)/%.html
	$(QUIET)test -d $(HTML_DEST) || {				\
		mkdir -p $(v) $(HTML_DEST);				\
	}
	@echo "  HTML    $@"
	$(QUIET)java -jar $(html-compiler)				\
			--compress-js --compress-css -o $@ $<

# Less CSS compilation phase
$(CSS_SRC)/%.css: $(CSS_SRC)/%.less $(HTML_BUILD_FILES)
	@echo "  LESS    $@"
	$(QUIET)lessc $< $@

$(CSS_DEST)/%.css: $(CSS_SRC)/%.css $(HTML_BUILD_FILES)
	$(QUIET)test -d $(CSS_DEST) || {				\
		mkdir -p $(v) $(CSS_DEST);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(BUILDIR)\/(.*)\.css/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).css,$(patsubst %.css,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  CSS     $(NAME)';			\
			java -jar $(css-compiler) --charset utf-8 -v	\
						--type css		\
						$< > $(NAME);		\
		};							\
		for h in $(HTML_BUILD_FILES); do				\
			sed -ri 's/(href=\")$(TARGET)(-[0-9a-f]{$(hash-length)})?(\.css\")/\1$(TARGET)-$(HASH)\3/' $$h; \
		done;							\
	)

$(JS_DEST)/%.js: $(JS_SRC)/%.js $(HTML_BUILD_FILES)
	$(QUIET)test -d $(JS_DEST) || {					\
		mkdir -p $(v) $(JS_DEST);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(BUILDIR)\/(.*)\.js/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).js,$(patsubst %.js,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  JS      $(NAME)';			\
			java -jar $(js-compiler) 			\
				--js=$< --js_output_file=$(NAME);	\
		};							\
		for h in $(HTML_BUILD_FILES); do			\
			sed -ri 's/(<script src=\")$(TARGET)(-[0-9a-f]{$(hash-length)})?(\.js\")/\1$(TARGET)-$(HASH)\3/' $$h; \
		done;							\
	)

$(assets-targets): $(assets-sources)
	$(QUIET)test -d $(dir $@) || {					\
		mkdir -p $(v) $(dir $@);				\
	}
	@echo "  CP      $@";
	$(QUIET)cp $(v) $< $@

##
## Publishing
##

#
# Export the contents of the $(BUILDIR) directory to $(LOCAL_SERVER_ROOT). If
# $(LOCAL_SERVER_ROOT) directory does not exist, fail loudly.
#
local:
	$(QUIET)test -d "$(LOCAL_SERVER_ROOT)" || {			\
		echo -n "fatal: destination directory " >&2;		\
		echo -n "'$(LOCAL_SERVER_ROOT)'" >&2;			\
		echo    " not found!" >&2; 				\
		echo >&2;						\
		exit 2;							\
	}
	$(QUIET)rsync -avh --delete					\
		"$(BUILDIR)/" "$(LOCAL_SERVER_ROOT)/"

##
## Documentation
##

docs: log

log:
	$(QUIET)make -C Documentation/log

##
## General
##

.PHONY: clean distclean

clean:
	$(QUIET)find $(BUILDIR) -type f | xargs --no-run-if-empty rm -v
	$(QUIET)rm -fv $(LESS_BUILD_FILES)

distclean: clean
	$(QUIET)rm -rfv $(BUILDIR)

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - generate build files'
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make local        - publish local site'
	@echo ''
	@echo 'Documentation:'
	@echo ''
	@echo '  make docs         - compile all documentation'
	@echo '  make log          - compile the log'
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - removes all the files of "clean" and some extras'
	@echo '  make help         - display this help text'
	@echo ''
