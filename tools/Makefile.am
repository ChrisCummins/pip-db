noinst_SCRIPTS = \
	dsa \
	png \
	$(NULL)

CLEANFILES = $(noinst_SCRIPTS)

# Generating executables for Node.js applications
%: %.js
	@echo '  INSTALL $<'
	$(QUIET)$(INSTALL) --mode=755 $< $@

# Generating executables shell scripts
%: %.sh
	@echo '  INSTALL $<'
	$(QUIET)$(INSTALL) --mode=755 $< $@
