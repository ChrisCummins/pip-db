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

clean-www clean-local:
	$(MAKE) -C www/ clean-www
endif

help:
	@echo ''
	@echo 'Compiling:'
	@echo ''
	@echo '  make all          - compile entire project'
	@echo ''
	@echo 'Publishing:'
	@echo ''
	@echo '  make install      - install project'
if HAVE_WWW_ROOT
	@echo '  make install-www  - publish local site'
endif
	@echo ''
	@echo 'General:'
	@echo ''
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - removes all configuration files'
	@echo '  make help         - display this help text'
	@echo ''
