#!/usr/bin/env node

/*
 * yaps.js - (Yet Another Protein Schema) CSV to YAPS conversion
 */

var lazy = require('lazy');
var fs  = require('fs');

// Print a message
var message = function (msg) {
  process.stderr.write(csv + ':' + lineCount + ' ' + msg + '\n');
};

// Print a warning message
var warning = function (msg) {
  message('WARNING: ' + msg);
};

// The dataset schema
var schema = [
  {name: 'names',         regex: '(?:protein(?: name[s]?)?)|(?:alternative name ?(?:[(]?s[)]?)?)'},
  {name: 'ec',            regex: 'e[.]?c[.]?'},
  {name: 'source',        regex: 'source(?: ?[(]?s[)]?)?'},
  {name: 'location',      regex: '(?:organ (?:and/or )?subcellular )?location(?: ?[(]?s[)]?)?'},
  {name: 'mw',            regex: 'm[.]?w[.]?'},
  {name: 'sub_no',        regex: 'sub(?:unit)? no[.]?'},
  {name: 'sub_mw',        regex: 'sub(?:unit)? m[.]?w[.]?'},
  {name: 'iso_enzymes',   regex: '(?:no[.]? (?:of )?)?iso[-]?enzymes'},
  {name: 'pi_exact',      regex: 'pi'},
  {name: 'pi_min',        regex: 'pi min(?:imum:)?(?: value)?'},
  {name: 'pi_max',        regex: 'pi max(?:imum)?(?: value)?'},
  {name: 'pi_major',      regex: 'pi (?:value of)? major component'},
  {name: 'temp',          regex: 'temperature(?: [(]?[ºo]?C[)]?)?'},
  {name: 'method',        regex: '(?:experimental )?method'},
  {name: 'full_text',     regex: 'full text'},
  {name: 'abstract_only', regex: '.*abstract(?: available)?'},
  {name: 'pubmed',        regex: 'pubmed(?: link)?'},
  {name: 'taxonomy',      regex: '(?:species )?taxonomy'},
  {name: 'sequence',      regex: '.*sequence'},
  {name: 'notes',         regex: 'notes'}
];

// The dataset delimiter
var delim = '\t';

// Determines and sets the indexes of schema values
var setSchemaIndexes = function(tokens) {

  var schemaIndex, getSchemaIndex = function (value) {
    for (var j in schema) {
      var re = new RegExp('^\\s*' + schema[j].regex + '\\s*$', 'i');

      if (value.match(re)) {
        return j;
      }
    }
  };

  // Instantiate the indexes array for each property in schema
  for (var i in schema)
    schema[i].indexes = [];

  // Iterate over each column in row
  for (var i in tokens) {
    schemaIndex = getSchemaIndex(tokens[i]);

    // Add the column index to schema indexes
    if (schemaIndex)
      schema[schemaIndex].indexes.push(Number(i));
    else
      warning('Ignoring unrecognised column "' + tokens[i] + '"');
  }

  // Warn about any schema property we have left uninitialised
  for (var i in schema) {
    if (!schema[i].indexes.length)
      warning('No data column found for property "' + schema[i].name + '"');
  }
};

// Process arguments
var argv = process.argv, argc = argv.length;

if (argc !== 3) {
  console.log('Usage <csv-file>');
  process.exit(1);
}

// Global state objects
var csv = argv[2];
var lineCount = 1;
var readStream = fs.createReadStream(csv);

readStream.on('error', function (error) {
  process.stderr.write('Unable to read file "' + csv + '"!\n');
});

new lazy(readStream).lines.forEach(function (buffer) {
  // Per-line callback
  var row = buffer.toString().replace(/\r/, ''); // Strip carriage return

  if (schema[0].indexes) // Body
    process.exit(0)
  else // Header row
    setSchemaIndexes(row.split(delim));

  lineCount++;
});
