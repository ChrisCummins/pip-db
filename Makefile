##
## Configuration
##

#
# The path to local test server.
#
# When 'make local' is executed, the contents of this directory are overwritten
# by the build files located in $(dest).
#
LOCAL_SERVER_ROOT = /var/www

# Path to build directory
dest = build

# Path to source directory
src = src

##
## Source files
##

less = styles.css

html-path = .
css-path = css
js-path = js

html-src = $(src)/$(html-path)
html-dest = $(dest)/$(html-path)

css-src = $(src)/$(css-path)
css-dest = $(dest)/$(css-path)

js-src = $(src)/$(js-path)
js-dest = $(dest)/$(js-path)

html = $(shell find $(html-src) -name '*.html' -exec basename {} \;)
html-sources = $(addprefix $(html-src)/,$(html))
html-targets = $(addprefix $(html-dest)/,$(html))

css = $(shell find $(css-src) -name '*.css' -exec basename {} \;)
css += $(less)
css-sources = $(addprefix $(css-src)/,$(css))
less-targets = $(addprefix $(css-src)/,$(less))
css-targets = $(addprefix $(css-dest)/,$(css))

js = $(shell find $(js-src) -name '*.js' -exec basename {} \;)
js-sources = $(addprefix $(js-src)/,$(js))
js-targets = $(addprefix $(js-dest)/,$(js))

##
## Build variables
##

# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))

ifdef V
	v = -v
endif

# The number of truncated hash characters to include in file name
hash-length = 16

site-contents =								\
	$(html-targets) $(extra-html)					\
	$(css-targets) $(less-targets) $(extra-css)			\
	$(js-targets) $(extra-js)					\
	$(assets-targets) 						\
	$(NULL)

clean-targets = $(dest)/* $(less-targets)

compilers = lib/build
html-compiler = $(compilers)/htmlcompressor.jar
css-compiler = $(compilers)/yuicompressor.jar
js-compiler = $(compilers)/closure-compiler.jar

##
## Compiling
##

all: $(site-contents)

$(html-dest)/%.html: $(html-src)/%.html
	$(QUIET)test -d $(html-dest) || {				\
		mkdir -p $(v) $(html-dest);				\
	}
	@echo "  HTML    $@"
	$(QUIET)java -jar $(html-compiler)				\
			--compress-js --compress-css -o $@ $<

# Less CSS compilation phase
$(css-src)/%.css: $(css-src)/%.less $(html-targets)
	@echo "  LESS    $@"
	$(QUIET)lessc $< $@

$(css-dest)/%.css: $(css-src)/%.css $(html-targets)
	$(QUIET)test -d $(css-dest) || {				\
		mkdir -p $(v) $(css-dest);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(dest)\/(.*)\.css/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).css,$(patsubst %.css,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  CSS     $(NAME)';			\
			java -jar $(css-compiler) --charset utf-8 -v	\
						--type css		\
						$< > $(NAME);		\
		};							\
		for h in $(html-targets); do				\
			sed -ri 's/(href=\")$(TARGET)(-[0-9a-f]{$(hash-length)})?(\.css\")/\1$(TARGET)-$(HASH)\3/' $$h; \
		done;							\
	)

$(js-dest)/%.js: $(js-src)/%.js $(html-targets)
	$(QUIET)test -d $(js-dest) || {					\
		mkdir -p $(v) $(js-dest);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(dest)\/(.*)\.js/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).js,$(patsubst %.js,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  JS      $(NAME)';			\
			java -jar $(js-compiler) 			\
				--js=$< --js_output_file=$(NAME);	\
		};							\
		for h in $(html-targets); do				\
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
# Export the $(dest) directory to $(LOCAL_SERVER_ROOT). If $(LOCAL_SERVER_ROOT)
# directory does not exist, fail loudly.
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
		"$(dest)/" "$(LOCAL_SERVER_ROOT)/"

##
## Documentation
##

docs: log

log:
	$(QUIET)make -C Documentation/log

##
## General
##

clean:
	$(QUIET)rm -rfv $(clean-targets)

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
	@echo '  make clean -      - remove most generated files'
	@echo '  make help         - display this help text'
	@echo ''
