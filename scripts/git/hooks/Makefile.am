GITDIR = $(top_srcdir)/.git/hooks

HOOKS = $(GITDIR)/pre-commit

if ENABLE_GIT_HOOKS
noinst_DATA = $(HOOKS)
endif

$(GITDIR)/%: %
	@echo '  GIT    $<'
	@$(INSTALL) --mode=755 $< $@
