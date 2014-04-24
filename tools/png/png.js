#!/usr/bin/env node

/*
 * png.js - Plausible Nonse Generator
 */
var path = require('path');
var fs  = require('fs');
var os = require("os");

// Print a message
var message = function(msg) {
  process.stderr.write(msg + '\n');
};

// Print a warning message
var warning = function(msg) {
  message('[WARNING]\t' + msg);
};

// Print an error message
var error = function(msg) {
  message('[ERROR!!]\t' + msg);
};

// Process arguments
var argv = process.argv, argc = argv.length;

if (argc !== 3 && argc !== 4) {
  console.log('Usage <tab-delimited-spreadsheet> [size]');
  process.exit(1);
}

var file = path.resolve(argv[2]);
var length = parseInt(argv[3]) || 100;

fs.readFile(file, 'utf8', function(err, data) {
  if (err) {
    console.log('Error: ' + err);
    return;
  }

  // An array of strings representing lines of input
  var lines = data.split('\n');

  // An array of lists, with each list containing a tab separated cell
  var table = (function(lines) {
    var data = [];

    for (var i in lines) {
      var row = lines[i].split('\t');

      if (row.length > 1)
        data.push(row);
    }

    return data;
  })(lines);

  // An array of lists, which each list representing all values for a
  // given field
  var lists = (function(table) {
    var data = [];

    for (var i = 0; i < table[0].length; i++) {
      var list = [];

      for (var j in table) {
        var cell = table[j][i];

        list.push(cell ? cell.trim() : '');
      }

      data.push(list);
    }

    return data;
  })(table);

  // Generate nonsense from seed `list' of `length'
  var getNonsense = function(list, length) {
    var startwords = [];
    var terminals = [];
    var wordTrees = {};

    for (var i = 0; i < list.length; i++) {
      var sentence = list[i].split(' ');

      terminals.push(sentence[sentence.length - 1]);
      startwords.push(sentence[0]);

      for (var j = 0; j < sentence.length - 1; j++) {
        var nextWord = sentence[j + 1];

        if (wordTrees[sentence[j]] !== undefined)
          wordTrees[sentence[j]].push(nextWord);
        else
          wordTrees[sentence[j]] = [nextWord];
      }
    }

    var rand = function(a) {
      var i = Math.floor(a.length * Math.random());

      return a[i];
    };

    var next = function() {
      var word = rand(startwords);
      var sentence = [word];

      while (wordTrees[word] !== undefined) {
        var nextWords = wordTrees[word];

        word = rand(nextWords);
        sentence.push(word);

        if (terminals[word] !== undefined)
          break;
      }

      return sentence.join(' ');
    };

    var data = [];
    for (var i = 0; i < length; i++)
      data.push(next());

    return data;
  };

  // Generate lists of nonsense
  var nonsense = (function(lists, length) {
    var table = [];

    for (var i in lists) {
      var list = getNonsense(lists[i].slice(1), length);
      list.unshift(lists[i][0])

      table.push(list);
    }

    return table;
  })(lists, length);

  // Generate output from nonsense lists
  var output = (function(nonsense, length) {
    var data = [];

    for (var i = 0; i < length + 1; i++) {
      var row = '';

      for (var j in nonsense)
        row += nonsense[j][i] + '\t';

      data.push(row);
    }

    return data;
  })(nonsense, length);

  console.log(output.join('\n'));
});
