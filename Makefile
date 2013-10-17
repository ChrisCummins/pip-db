##
## Configuration
##

# Path to local test server
local_server_root = /var/www

# Path to build directory
dest = build

# Path to source directory
src = src

##
## Source files
##

html =				\
	index.html		\
	results.html		\
	$(NULL)

css =				\
	css/bootstrap.css	\
	css/styles.css		\
	$(NULL)

js =				\
	js/bootstrap.js		\
	$(NULL)

assets =

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

html-path = .
html-source-path = $(src)/$(html-path)
html-target-path = $(dest)/$(html-path)
html-sources = $(addprefix $(html-source-path)/,$(html))
html-targets = $(addprefix $(html-target-path)/,$(html))

css-sources = $(addprefix $(src)/,$(css))
css-targets = $(addprefix $(dest)/,$(css))

js-sources = $(addprefix $(src)/,$(js))
js-targets = $(addprefix $(dest)/,$(js))

assets-sources = $(addprefix $(src)/,$(assets))
assets-targets = $(addprefix $(dest)/,$(assets))

site-contents =								\
	$(html-targets) $(extra-html)					\
	$(css-targets) $(extra-css)					\
	$(js-targets) $(extra-js)					\
	$(assets-targets)						\
	$(NULL)

clean-targets = $(dest)/*

compilers = lib/build
html-compiler = $(compilers)/htmlcompressor.jar
css-compiler = $(compilers)/yuicompressor.jar
js-compiler = $(compilers)/closure-compiler.jar

##
## Compiling
##

all: $(site-contents)

$(html-targets): $(html-sources)
	$(QUIET)test -d $(html-target-path) || {			\
		mkdir -p $(v) $(html-target-path);			\
	}
	$(QUIET)for f in $(html); do					\
		echo "  HTML    $(html-source-path)/$$f";		\
		java -jar $(html-compiler) 				\
					--compress-js 			\
					--compress-css 			\
					-o $(html-target-path)/$$f	\
					$(html-source-path)/$$f;	\
	done

# Less CSS compilation phase
less-sources = $(shell find $(css-source-path) -name '*.less')

$(css-sources): $(less-sources)
	$(QUIET)(							\
		NAME="$(@:.css=.less)"; 				\
		if [ -f "$$NAME" ]; then				\
			echo "  LESS    $$NAME";			\
			lessc "$$NAME" "$@";				\
		fi;							\
	)

$(css-targets): $(css-sources)
	$(QUIET)test -d $(dir $@) || {					\
		mkdir -p $(v) $(dir $@);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET_NAME := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(dest)\/(.*)\.css/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).css,$(patsubst %.css,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  CSS     $(NAME)';			\
			java -jar $(css-compiler) --charset utf-8 -v	\
						--type css		\
						$< > $@;		\
			for h in $(html-targets); do			\
				sed -ri 's/(href=\")$(TARGET_NAME)(-[0-9a-f]{$(hash-length)})?(\.css\")/\1$(TARGET_NAME)-$(HASH)\3/' $$h; \
			done;						\
		}							\
	)

$(js-targets): $(js-sources)
	$(QUIET)test -d $(dir $@) || {					\
		mkdir -p $(v) $(dir $@);				\
	}
	$(eval HASH := $(shell md5sum $< | cut -c1-$(hash-length)))
	$(eval TARGET_NAME := $(subst /,\/,$(shell echo "$@" | sed -r 's/$(dest)\/(.*)\.js/\1/')))
	$(eval NAME := $(addsuffix -$(HASH).js,$(patsubst %.js,%,$@)))
	$(QUIET)(							\
		test -f $(NAME) || {					\
			echo '  JS      $(NAME)';			\
			java -jar $(js-compiler) 			\
				--js=$< --js_output_file=$@;		\
			for h in $(html-targets); do			\
				sed -ri 's/(<script src=\")$(TARGET_NAME)(-[0-9a-f]{$(hash-length)})?(\.js\")/\1$(TARGET_NAME)-$(HASH)\3/' $$h; \
			done;						\
		};							\
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

local:
	$(QUIET)test -d "$(local_server_root)" || {	\
		echo "Local directory '$(local_server_root)' not found!"; \
		exit 2;					\
	}
	$(QUIET)rsync -avh --delete			\
		--exclude .git/ 			\
		--exclude Makefile			\
		"$(dest)/" "$(local_server_root)/"

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
	@echo 'General:'
	@echo ''
	@echo '  make clean -      - remove most generated files'
	@echo '  make help         - display this help text'
	@echo ''
