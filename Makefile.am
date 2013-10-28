# Use V=1 to see full verbosity
QUIET_  = @
QUIET   = $(QUIET_$(V))
export QUIET

SUBDIRS = \
	Documentation \
	tools \
	www \
	$(NULL)

local:
	@test -d "$(WWW_ROOT)" || { \
		echo -n "fatal: destination directory " >&2; \
		echo -n "'$(WWW_ROOT)'" >&2; \
		echo    " not found!" >&2; \
		echo >&2; \
		exit 2; \
	}
	rsync -avh --delete "$(WWW_BUILD)/" "$(WWW_ROOT)/"

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - compile entire project'
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make local        - publish local site'
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - removes all configuration files'
	@echo '  make help         - display this help text'
	@echo ''
