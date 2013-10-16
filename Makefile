##
## Configuration
##

# Path to local test server
local_server_root = /var/www

# Path to build directory
dest = build

##
## Source files
##

html =				\
	index.html		\
	results.html		\
	$(NULL)

css =				\
	bootstrap.css		\
	styles.css		\
	$(NULL)

js =				\
	bootstrap.js		\
	$(NULL)

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
html-source-path = src/$(html-path)
html-target-path = $(dest)/$(html-path)
html-sources = $(addprefix $(html-source-path)/,$(html))
html-targets = $(addprefix $(html-target-path)/,$(html))

css-path = css
css-source-path = src/$(css-path)
css-target-path = $(dest)/$(css-path)
css-sources = $(addprefix $(css-source-path)/,$(css))
css-targets = $(addprefix $(css-target-path)/,$(css))
css-build = `cat $(css-targets) | mda5sum | cut -c1-8`.css

js-path = js
js-source-path = src/$(js-path)
js-target-path = $(dest)/$(js-path)
js-sources = $(addprefix $(js-source-path)/,$(js))
js-targets = $(addprefix $(js-target-path)/,$(js))

clean-targets =								\
	$(html-target-path)/*						\
	$(css-target-path)/*						\
	$(js-target-path)/*						\
	$(NULL)

compilers = lib/build
html-compiler = $(compilers)/htmlcompressor.jar
css-compiler = $(compilers)/yuicompressor.jar
js-compiler = $(compilers)/closure-compiler.jar

##
## Compiling
##

all: $(html-targets) $(css-targets) $(js-targets)

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
	$(QUIET)test -d $(css-target-path) || {				\
		mkdir -p $(v) $(css-target-path);			\
	}
	$(QUIET)for f in $(css); do					\
		HASH=`md5sum $(css-source-path)/$$f | cut -c1-$(hash-length)`; \
		NAME=$${f%.*}-$$HASH.css;				\
		test -f "$(css-target-path)/$$NAME" || {		\
			echo "  CSS     $(css-source-path)/$$f";	\
			java -jar $(css-compiler) --charset utf-8 -v	\
						--type css		\
						$(css-source-path)/$$f	\
						 > $(css-target-path)/$$NAME; \
			for h in $(html-targets); do			\
				sed -ri "s/(href=\"$(css-path)\/)$${f%.*}(-[0-9a-f]{$(hash-length)})?.css(\")/\1$$NAME\3/" $$h; \
			done;						\
		}							\
	done

$(js-targets): $(js-sources)
	$(QUIET)test -d $(js-target-path) || {				\
		mkdir -p $(v) $(js-target-path);			\
	}
	$(QUIET)for f in $(js); do					\
		HASH=`md5sum $(js-source-path)/$$f | cut -c1-$(hash-length)`;	\
		NAME=$${f%.*}-$$HASH.js;				\
		test -f "$(js-target-path)/$$NAME" || {			\
			echo "  JS      $(js-source-path)/$$f";		\
			java -jar $(js-compiler) 			\
				--js=$(js-source-path)/$$f		\
				--js_output_file=$(js-target-path)/$$NAME; \
			for h in $(html-targets); do			\
				sed -ri "s/(<script src=\"$(js-path)\/)$${f%.*}(-[0-9a-f]{$(hash-length)})?.js(\")/\1$$NAME\3/" $$h; \
			done;						\
		}							\
	done

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
