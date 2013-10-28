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
