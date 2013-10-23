##
## Top level Makefile
##

# Subdirectory for website source code
WWW_DIR = www

# Subdirectories for recursive make rules
SUBDIRS =								\
	Documentation/log						\
	tools/dsa							\
	$(WWW_DIR)							\
	$(NULL)

# Usage: $(call recursive-make,target,dirs)
define recursive-make
	for d in $2; do							\
		make -C "$$d" $1;					\
	done;
endef

all:
	$(call recursive-make,all,$(SUBDIRS))

local:
	$(call recursive-make,local,$(WWW_DIR))

clean:
	$(call recursive-make,clean,$(SUBDIRS))

distclean:
	$(call recursive-make,distclean,$(SUBDIRS))

.PHONY: help

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - full project build'
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make local        - publish local site'
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - removes all the files of "clean" and some extras'
	@echo '  make help         - display this help text'
	@echo ''
