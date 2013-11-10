all-local:
	@echo '  RSYNC  lib/twig'
	@rsync -a ../twig $(WWW_BUILD)/lib --exclude 'Makefile*'

CLEANFILES = $(shell find $(WWW_BUILD)/lib/twig)
