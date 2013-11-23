noinst_SCRIPTS = \
	dsa \
	ghi \
	mkrelease \
	png \
	sloccount \
	workflow \
	$(NULL)

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
	@echo '  NODE   $@'
	@$(INSTALL) --mode=755 $< $@

# Generating executables from shell scripts
%: %.sh
	@echo '  SHELL  $@'
	@$(INSTALL) --mode=755 $< $@

# Generating executables from ruby scripts
%: %.rb
	@echo '  RUBY   $@'
	@$(INSTALL) --mode=755 $< $@
