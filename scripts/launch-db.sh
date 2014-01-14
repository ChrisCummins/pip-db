#!/bin/sh
#
# Launch a local user instance of the PostgreSQL database.

sudo mkdir -pv /var/run/postgresql
sudo chown "$(whoami)":"$(whoami)" -R /var/run/postgresql

postgres -D pg
