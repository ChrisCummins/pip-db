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

          function rstring(length) {
            var string = "";
            var space = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890";

            for (var i = 0; i < length; i++)
              string += space.charAt(rint(0, space.length));

            return string;
          }

          function generateLatinBinomial() {
            var genus = rentry(payload.latin);
            var species = rentry(payload.latin);

            return genus + " " + species;
          }

          switch (i) {
          case 0:
            if (type)
              return "E.C.";
            else if (Math.random() < 0.8)
              return (rint(1, 3) + "." +
                      rint(1, 10) + "." +
                      rint(1, 30) + "." +
                      rint(1, 30));
            else
              return "";
            break;
          case 1:
            return type ? "Protein" : rentry(payload.proteins);
            break;
          case 2:
            if (type)
              return "Alternative name(s)";
            else if (Math.random() < 0.9)
              return rentry(payload.proteins);
            else
              return "";
            break;
          case 3:
            return type ? "Source" : generateLatinBinomial();
            break;
          case 4:
            if (type)
              return "Organ";
            else if (Math.random() < 0.6)
              return rentry(payload.organs);
            else
              return "";
            break;
          case 5:
            if (type)
              return "M.W";
            else if (Math.random() < 0.9)
              return rint(45, 230) * 1000;
            else
              return "";
            break;
          case 6:
            return type ? "Subunit No." : "data";
            break;
          case 7:
            return type ? "Subunit M.W" : "data";
            break;
          case 8:
            if (type)
              return "No of Iso-enzymes";
            else if (Math.random() < 0.02)
              return rint(1, 10);
            else
              return "";
            break;
          case 9:
            return type ? "pI maximum value" : "data";
            break;
          case 10:
            return type ? "pI range min" : "data";
            break;
          case 11:
            return type ? "pI range max" : "data";
            break;
          case 12:
            return type ? "pI value of major component" : "data";
            break;
          case 13:
            return type ? "pI" : "data";
            break;
          case 14:
            if (type)
              return "Temperature (C)";
            else if (Math.random() < 0.6 && Math.random() > 0.3)
              return rint(10,37) + "C";
            else if (Math.random() < 0.3)
              return rint(10,20) + "-" + rint(21,37) + "C"
            else
              return "";
            break;
          case 15:
            if (type)
              return "Method";
            else if (Math.random() < 0.8)
              return rentry(payload.methods);
            else
              return "Not available";
            break;
          case 16:
            return type ? "Valid sequence(s) available" : "data";
            break;
          case 17:
            if (type)
              return "Protein sequence";
            else if (Math.random() < 0.6)
              return "http://www.ncbi.nlm.nih.gov/protein/" + rstring(8);
            else
              return "";
            break;
          case 18:
            if (type)
              return "Species Taxonomy";
            else if (Math.random() < 0.95)
              return "http://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?lvl=0&id=" + rstring(8);
            else
              return "";
            break;
          case 19:
            if (type)
              return "Full Text";
            else if (Math.random() < 0.8)
              return "http://www.ncbi.nlm.nih.gov/pmc/articles/PMC1177738/pdf/biochemj" + rstring(8);
            else
              return "";
            break;
          case 20:
            if (type)
              return "Abstract";
            else if (Math.random() < 0.02)
              return "http://www.sciencedirect.com/science/article/pii/" + rstring(16);
            else
              return "";
            break;
          case 21:
            if (type)
              return "Pubmed";
            else if (Math.random() < 0.9)
              return "http://www.ncbi.nlm.nih.gov/pubmed/" + rstring(8);
            else
              return "";
            break;
          case 22:
            if (type)
              return "Notes";
            else if (Math.random() < 0.02)
              return rentry(payload.notes);
            else
              return "";
            break;
          }
        }

        var row = "";

        for (var i = 0; i < 23; i++) {
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
