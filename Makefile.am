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

# Export build to local webserver
local:
	$(MAKE) -C www/ local

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
