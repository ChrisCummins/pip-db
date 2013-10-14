##
## Configuration
##

# Path to local test server
local_server_root = /var/www

# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))

less =							\
	css/styles.less					\
	$(NULL)

css = $(less:.less=.css)

%.css: %.less
	lessc $< $@

##
## Publishing
##
local: $(less)
	$(QUIET)test -d "$(local_server_root)" || {	\
		echo "Local directory '$(local_server_root)' not found!"; \
		exit 2;					\
	}
	$(QUIET)rsync -avh --delete			\
		--exclude .git/ 			\
		--exclude Makefile			\
		"$(PWD)/" "$(local_server_root)/"

##
## General
##
help:
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make local        - publish local site'
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make help         - display this help text'
	@echo ''
