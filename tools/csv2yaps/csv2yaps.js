#!/usr/bin/env node

/*
 * yaps.js - (Yet Another Protein Schema) CSV to YAPS conversion
 */

var lazy = require('lazy');
var fs  = require('fs');

// Print a message
var message = function (msg) {
  process.stderr.write('At line ' + lineCount + ':\t' + msg + '\n');
};

// Print a warning message
var warning = function (msg) {
  message('[WARNING]\t' + msg);
};

// Print an error message
var error = function (msg) {
  message('[ERROR!!]\t' + msg);
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
  {name: 'ref_full',      regex: 'full text'},
  {name: 'ref_abstract',  regex: '.*abstract(?: available)?'},
  {name: 'ref_pubmed',    regex: 'pubmed(?: link)?'},
  {name: 'ref_taxonomy',  regex: '(?:species )?taxonomy'},
  {name: 'ref_sequence',  regex: '.*sequence'},
  {name: 'notes',         regex: 'notes'}
];

// The dataset delimiter
var delim = '\t';

var nullValueRe = new RegExp([
  '^(',
  '|(not given( \\(N[.]?G[.]?\\))?)',
  '|(n[.]?g[.]?)',
  '|(not available( \\(N[./]?A[.]?\\))?)',
  '|(no entry)',
  '|(n[./]?a[.]?)',
  '|(not applicable)',
  '|(unavailable)',
  ')$'
].join(''), 'i');

// Determines and sets the indexes of schema values
var setSchemaIndexes = function (tokens) {

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

// Formalise a set of tokens
var tokens2Row = function (tokens) {

  var row = {};

  for (var i in schema) {

    // Compute the values from the row
    var values = (function () {
      var values = [];
      var schemaProp = schema[i];
      var prop = yaps[schemaProp.name];
      var str;

      for (var j in schemaProp.indexes) {
        str = tokens[schemaProp.indexes[j]];

        if (str !== undefined)
          str.trim();
        else {
          error('Line is too short. No column for property "' +
                schemaProp.name + '". Results may be corrupted.');
        }

        if (str) { // Process value
          if (str.match(nullValueRe)) // Warn if we're ignoring the value
            warning('Ignoring value "' + str + '" for property "' +
                    schemaProp.name + '"');
          else
            values.push(str);
        }
      }

      return values.length ? values : undefined;
    })();

    if (values)
      row[schema[i].name] = values;
  }

  return row;
};

// Convert a row object into a Yaps object
var row2Yaps = function (row) {

  var yaps = {};

  // Protein names
  if (row.names) {
    yaps.names = (function (arr) {
      var n = [];

      for (var i in arr)
        n.push.apply(n, arr[i].split('/'));

      for (var i in n)
        n[i] = n[i].trim().replace(/\.$/, '');

      return n;
    })(row.names);
  }

  // Enzyme commission number
  if (row.ec)
    yaps.ec = row.ec[0];

  // Source
  if (row.source)
    yaps.source = row.source[0];

  // Location
  if (row.location)
    yaps.location = row.location[0];

  // MW
  if (row.mw) {
    var c = row.mw[0].split(/ ?[-\/] ?/);

    yaps.mw_min = c[0];
    yaps.mw_max = c.length > 1 ? c[1] : c[0];
  }

  // Subunit No.
  if (row.sub_no)
    yaps.sub_no = row.sub_no[0];

  // Subunit M.W
  if (row.sub_mw)
    yaps.sub_mw = row.sub_mw[0];

  // No of Iso-Enzymes
  if (row.iso_enzymes)
    yaps.iso_enzymes = row.iso_enzymes[0];

  // pI min & pI Max
  if (row.pi_exact) {
    yaps.pi_min = row.pi_exact[0];
    yaps.pi_max = row.pi_exact[0];
  } else {

    if (row.pi_min)
      yaps.pi_min = row.pi_min[0];

    if (row.pi_max)
      yaps.pi_max = row.pi_max[0];
  }

  // pI major
  if (row.pi_major)
    yaps.pi_major = row.pi_major[0];

  // Temperature
  if (row.temp) {
    var c = row.temp[0].replace(/[ºْ]/, '').split(/ ?[-\/] ?/);

    yaps.temp_min = c[0];
    yaps.temp_max = c.length > 1 ? c[1] : c[0];
  }

  // Experimental method
  if (row.method)
    yaps.method = row.method[0];

  // References
  if (row.ref_full)
    yaps.ref_full = row.ref_full[0];

  if (row.ref_abstract)
    yaps.ref_abstract = row.ref_abstract[0];

  if (row.ref_pubmed)
    yaps.ref_pubmed = row.ref_pubmed[0];

  if (row.ref_taxonomy)
    yaps.ref_taxonomy = row.ref_taxonomy[0];

  if (row.ref_sequence)
    yaps.ref_sequence = row.ref_sequence[0];

  // Notes
  if (row.notes)
    yaps.notes = row.notes[0];

  return yaps;
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
var yaps = [];

readStream.on('error', function (error) {
  process.stderr.write('Unable to read file "' + csv + '"!\n');
});

new lazy(readStream).on('end', function () {
  // End of processing callback
  console.log(JSON.stringify(yaps));
}).lines.forEach(function (buffer) {
  // Per-line callback
  var line = buffer.toString().replace(/\r/, ''); // Strip carriage return
  var tokens = line.split(delim);

  if (schema[0].indexes) // Body
    yaps.push(row2Yaps(tokens2Row(tokens)));
  else // Header line
    setSchemaIndexes(tokens);

  lineCount++;
});
