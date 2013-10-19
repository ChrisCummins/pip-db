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

      /* @type The type of row to generate. 1 for header row, else 0 for
       *       data. */
      function generateRow(type, format) {

        function generateCol(i, type) {

          /* Return random entry from array */
          function rentry(array) {
            return array[Math.floor(Math.random() * (array.length + 1))];
          }

          function rint(low, high) {
            return Math.floor(Math.random() * (high - low + 1) + low);
          }

          function generateLatinBinomial() {
            var genus = rentry(payload.latin);
            var species = rentry(payload.latin);

            return genus + " " + species;
          }

          switch (i) {
          case 0:
            return type ? "Protein" : rentry(payload.proteins);
            break;
          case 1:
            if (type)
              return "Alternative name(s)";
            else if (Math.random() < 0.9) {
              return rentry(payload.proteins);
            } else {
              return "";
            };
            break;
          case 2:
            return type ? "Source" : generateLatinBinomial();
            break;
          case 3:
            if (type) {
              return "Organ";
            } else if (Math.random() < 0.6) {
              return rentry(payload.organs);
            } else {
              return "";
            };
            break;
          case 4:
            if (type)
              return "M.W";
            else if (Math.random() < 0.9)
              return rint(45, 230) * 1000;
            else
              return "";
            break;
          case 5:
            return type ? "Subunit M.W" : "data";
            break;
          case 6:
            if (type)
              return "No of Iso-enzymes";
            else if (Math.random() < 0.02)
              return rint(1, 10);
            else
              return "";
            break;
          case 7:
            return type ? "pI maximum value" : "data";
            break;
          case 8:
            return type ? "pI range" : "data";
            break;
          case 9:
            return type ? "pI value of major component" : "data";
            break;
          case 10:
            return type ? "pI" : "data";
            break;
          case 11:
            return type ? "Temperature (C)" : "data";
            break;
          case 12:
            return type ? "Method" : "data";
            break;
          case 13:
            return type ? "Valid sequence(s) available" : "data";
            break;
          case 14:
            return type ? "Protein sequence" : "data";
            break;
          case 15:
            return type ? "Species Taxonomy" : "data";
            break;
          case 16:
            return type ? "Full Text" : "data";
            break;
          case 17:
            return type ? "Abstract" : "data";
            break;
          case 18:
            return type ? "Pubmed" : "data";
            break;
          case 19:
            return type ? "Notes" : "data";
            break;
          }
        }

        var row = "";

        for (var i = 0; i < 20; i++) {
          var text = generateCol(i, type);

          switch (format) {
            case "CSV":
              row += '"';
              row += text;
              row += '", ';
              break;
          }
        }

        return row;
      }

      var results = "";

      /* Generate header row */
      results += generateRow(1, format) + "\n";

      for (var i = 0; i < size; i++) {
        results += generateRow(0, format) + "\n";
      }

      return results;
    }

    showDataset(generateDataset(getDatasetFormat(),
                                getDatasetSize()));
  });

}).call(png);
