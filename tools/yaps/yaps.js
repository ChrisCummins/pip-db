#!/usr/bin/env node

/*
 * yaps.js - Yet Another Protein Schema
 */

var lazy = require("lazy");
var fs  = require("fs");

var processLine = function (line) {

};

var argv = process.argv, argc = argv.length;

if (argc !== 3) {
  console.log('Usage <csv-file>');
  process.exit(1);
}

var csv = argv[2];
var readStream = fs.createReadStream(csv);

readStream.on('error', function (error) {
  console.log('Unable to read file "' + csv + '"!');
});

new lazy(readStream)
  .lines
  .forEach(processLine);
