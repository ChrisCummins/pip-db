SUBDIRS = \
	build \
	design \
	Documentation \
	tools \
	www \
	$(NULL)

EXTRA_DIST = \
	autogen.sh \
	$(NULL)

if HAVE_WWW_ROOT
# Export build to local webserver
install-www local:
	$(MAKE) -C www/ install-www
endif

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - compile entire project'
if HAVE_WWW_ROOT
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make local        - publish local site'
endif
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - removes all configuration files'
	@echo '  make help         - display this help text'
	@echo ''
