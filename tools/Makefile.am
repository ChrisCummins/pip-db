noinst_SCRIPTS = \
	dsa \
	ghi \
	png \
	sloccount \
	$(NULL)

if HAVE_GIT_FLOW
noinst_SCRIPTS += workflow mkrelease

dist_bin_SCRIPTS = pipbot
endif

CLEANFILES = $(noinst_SCRIPTS) $(dist_bin_SCRIPTS)

EXTRA_DIST = \
	dsa.sh.in \
	png-payload.js \
	png.js \
	README.md \
	workflow.sh \
	$(NULL)

# Generating executables from Node.js scripts
%: %.js
	@echo '  NODE   $@'
	@$(INSTALL) --mode=755 $< $@

# Generating executables from python scripts
%: %.py
	@echo '  PYTHON $@'
	@$(INSTALL) --mode=755 $< $@

# Generating executables from shell scripts
%: %.sh
	@echo '  SHELL  $@'
	@$(INSTALL) --mode=755 $< $@

# Generating executables from ruby scripts
%: %.rb
	@echo '  RUBY   $@'
	@$(INSTALL) --mode=755 $< $@
