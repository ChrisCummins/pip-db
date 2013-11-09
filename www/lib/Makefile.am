all-local:
	@echo '  RSYNC  lib'
	@rsync -a ../lib $(WWW_BUILD)/ --exclude 'Makefile*'

CLEANFILES = $(shell find $(WWW_BUILD)/lib)
