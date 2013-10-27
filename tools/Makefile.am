noinst_SCRIPTS = \
	png \
	$(NULL)

CLEANFILES = $(noinst_SCRIPTS)

# Generating executables for Node.js applications
%: %.js
	@echo '  INSTALL $<'
	$(QUIET)$(INSTALL) --mode=755 $< $@
