noinst_SCRIPTS = \
	dsa \
	ghi \
	png \
	$(NULL)

dist_bin_SCRIPTS = fetch-fasta pipbot

CLEANFILES = $(noinst_SCRIPTS) $(dist_bin_SCRIPTS)

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
