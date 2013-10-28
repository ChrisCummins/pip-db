noinst_SCRIPTS = \
	dsa \
	png \
	$(NULL)

CLEANFILES = $(noinst_SCRIPTS)

# Generating executables from Node.js scripts
%: %.js
	$(INSTALL) --mode=755 $< $@

# Generating executables from shell scripts
%: %.sh
	$(INSTALL) --mode=755 $< $@
