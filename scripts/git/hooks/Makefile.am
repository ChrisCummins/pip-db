GITDIR = $(top_srcdir)/.git/hooks

HOOKS = $(GITDIR)/pre-commit

noinst_DATA = $(HOOKS)

$(GITDIR)/%: %
	@echo '  GIT    $<'
	@$(INSTALL) --mode=755 $< $@
