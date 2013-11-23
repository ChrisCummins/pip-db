SUBDIRS = jar

EXTRA_DIST = README.md

if ENABLE_INSTALL
install-data-local:
	@rsync -avh --delete "$(WWW_BUILD)/" "$(WWW_ROOT)/"

uninstall-local:
	rm -rvf "$(WWW_ROOT)"
endif

CLEANFILES = $(shell find "$(WWW_BUILD)" -type f)
