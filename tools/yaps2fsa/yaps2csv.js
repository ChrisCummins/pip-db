#!/usr/bin/env node

/*
 * yaps2csv.js - (Yet Another Protein Schema) YAPS to FASTA sequences
 * file.
 *
 * Copyright 2014 Chris Cummins.
 *
 * This file is part of pip-db.
 *
 * pip-db is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pip-db is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pip-db.  If not, see <http://www.gnu.org/licenses/>.
 */
var VERSION = 4;

var path = require('path');
var lazy = require('lazy');
var fs  = require('fs');
var os = require("os");

// Print a message
var message = function (msg) {
  process.stderr.write(msg + '\n');
};

// Print a warning message
var warning = function (msg) {
  message('[WARNING]\t' + msg);
};

// Print an error message
var error = function (msg) {
  message('[ERROR!!]\t' + msg);
};

// Process arguments
var argv = process.argv, argc = argv.length;

if (argc !== 3) {
  console.log('Usage <yaps-file>');
  process.exit(1);
}

var file = path.resolve(argv[2]);

fs.readFile(file, 'utf8', function (err, data) {
  if (err) {
    console.log('Error: ' + err);
    return;
  }

  var yaps = JSON.parse(data);

  if (yaps['Encoding'] !== "yaps") {
    error("input file format error!");
    process.exit(1);
  }

  if (yaps['Version'] !== VERSION) {
    error("invalid yaps version! Expected: '" + VERSION +
          "', found: '" + yaps['Version']+ "'");
    process.exit(1);
  }

  yaps['Records'].forEach(function(r) {
    var name = r['Sequence-Name'], data = r['Sequence-Data'];

    if (name && data) {
      console.log(r['Sequence-Name'] + '\n' + r['Sequence-Data'] + '\n');
    }
  });
});
