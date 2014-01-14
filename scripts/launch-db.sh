#!/bin/sh

sudo mkdir -pv /var/run/postgresql
sudo chown "$(whoami)":"$(whoami)" -R /var/run/postgresql

postgres -D pg
