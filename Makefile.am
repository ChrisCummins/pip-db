# Copyright 2014 Chris Cummins.
#
# This file is part of pip-db.
#
# pip-db is free software: you can redistribute it and/or modify it
# under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# pip-db is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with pip-db.  If not, see <http://www.gnu.org/licenses/>.
#
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
