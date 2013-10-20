var png = png || {};

(function() {
  'use strict';

  if (!payload) {
    alert('No payload found!');
  }

  $('#generate').click(function() {

    function showDataset(dataset) {
      $('#results').text(dataset);
      $('#results').show();
    }

    function getDatasetSize() {
      return parseInt($('#target-size option:selected').text());
    }

    function getDatasetFormat() {
      return $('#target-type option:selected').text();
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

    showDataset(generateDataset(getDatasetFormat(),
                                getDatasetSize()));
  });

}).call(png);
