var png = png || {};

(function() {
  'use strict';

  if (!payload) {
    alert('No payload found!');
  }

  $('#generate').click(function() {

    function getDatasetSize() {
      return parseInt($('#target-size option:selected').text());
    }

    function getDatasetFormat() {
      return $('#target-type option:selected').text();
    }

    function getFileName() {
      var f = $('#target-filename').val();

      if (f != '')
        return f;
      else
        return 'dataset.csv';
    }

    function generateDataset(format, size) {
      var results = '';

      /* Generate header row */
      var header = new Tuple(1)

      switch (format) {
      case 'CSV':
        results += header.toCSV() + '\n';
        break;
      }

      for (var i = 0; i < size; i++) {
        var row = new Tuple();

        switch (format) {
          case 'CSV':
            results += row.toCSV() + '\n';
            break;
        }
      }

      return results;
    }

    var format = getDatasetFormat();
    var dataset = generateDataset(format, getDatasetSize());

    switch (format) {
      case 'CSV':
        var header = 'data:text/csv;charset=utf-8,';
        break;
    }

    var uri = encodeURI(header + dataset);
    var a = document.createElement('a');
    var filename = getFileName();

    a.setAttribute('href', uri);
    a.setAttribute('download', filename);
    a.click();
  });

}).call(png);
