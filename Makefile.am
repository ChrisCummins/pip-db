SUBDIRS = \
	resources \
	scripts \
	$(NULL)

if ENABLE_DOCUMENTATION
SUBDIRS += Documentation
endif

if ENABLE_TOOLS
SUBDIRS += tools
endif

clean-local:
	@echo '  LEIN   clean'
	@lein clean

# Remove node installations on distclean
distclean-local:
	@rm -rvf $(shell find . -name '*node_modules*') build/node config.summary

.PHONY: test run-server run-db

test:
	@lein test

run: run-server

run-server:
	@./scripts/launch-server.sh

run-db:
	@./scripts/launch-db.sh

help:
	@echo '  make all          - compile entire project'
	@echo '  make test         - execute test suite'
	@echo '  make clean        - remove most generated files'
	@echo '  make distclean    - remove all generated files'
	@echo '  make help         - display this help text'
