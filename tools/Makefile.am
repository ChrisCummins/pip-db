if ENABLE_EXTRA_TOOLS
noinst_SCRIPTS = \
	dsa \
	png \
	workflow \
	$(NULL)
endif

CLEANFILES = $(noinst_SCRIPTS)

EXTRA_DIST = \
	dsa.sh.in \
	png-payload.js \
	png.js \
	pngrc \
	README.md \
	workflow.sh \
	$(NULL)

# Generating executables from Node.js scripts
%: %.js
	$(INSTALL) --mode=755 $< $@

# Generating executables from shell scripts
%: %.sh
	$(INSTALL) --mode=755 $< $@
