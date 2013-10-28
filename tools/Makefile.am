noinst_SCRIPTS = \
	dsa \
	png \
	$(NULL)

CLEANFILES = $(noinst_SCRIPTS)

# Generating executables from Node.js scripts
%: %.js
	@echo '  INSTALL $<'
	$(QUIET)$(INSTALL) --mode=755 $< $@

# Generating executables from shell scripts
%: %.sh
	@echo '  INSTALL $<'
	$(QUIET)$(INSTALL) --mode=755 $< $@
