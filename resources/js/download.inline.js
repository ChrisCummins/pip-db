(function () {
    'use strict';

    /*
     * Convert a JSON object into a CSV encoded string.
     */
    var json2csv = function(o, separator) {
        separator = separator || ',';
        var re = new RegExp(separator, 'g');
        var csv = '';
        var EOL = '\r\n';

        // Add an item to a line
        var appendItemToLine = function(item, line) {

            // Escape a string
            var escape = function (text) {
                if (text !== undefined)
                    return '"' + String(text).replace(re, '\\' + separator) + '"';
                else // Don't return "undefined".
                    return '""';
            }

            if (line !== '')
                line += separator;

            return line + escape(item);
        };

        // Header row
        for (var j in records[0])
            csv = appendItemToLine(j, csv);

        csv += EOL;

        // JSON contents
        for (var i = 0; i < o.length; i++) {
            var line = '', item = o[i];

            for (var j in item)
                line = appendItemToLine(item[j], line);

            csv += line + EOL;
        }

        return csv;
    };


    /*
     * PIP-DB DOWNLOADS PAGE
     */

    // YAPS schema
    var properties = [
        'Protein-Names',
        'EC',
        'Source',
        'Location',
        'MW-Min',
        'MW-Max',
        'Subunit-No',
        'Subunit-MW',
        'No-Of-Iso-Enzymes',
        'pI-Min',
        'pI-Max',
        'pI-Major-Component',
        'Temperature-Min',
        'Temperature-Max',
        'Method',
        'PubMed',
        'Species-Taxonomy',
        'Protein-Sequence',
        'Created-At',
        'Available-At'
    ];

    /*
     * Generate our records data from the results map.
     */
    var records = [];
    data['Records'].forEach(function (r) {
        var record = {};

        properties.forEach(function (i) {
            record[i] = r[i];
        });

        records.push(record);
    });

    // UI components
    var $table = $('#table');
    var $tbody = $(' tbody', $table);
    var $text = $('#text');

    var $ff = $('ul#ff');
    var $download = $('#download');
    var $noResultsMessage = $('#no-results');

    // Global state
    var format = 'csv';
    var mime = 'text/csv';

    /*
     * File format selection
     */
    var transitionDuration = 300;


    var showTable = function () {
        $table.fadeIn(transitionDuration);
        $text.hide();
    };

    var showText = function () {
        $table.hide();
        $text.fadeIn(transitionDuration);
    };

    // Generate text formatted data
    var downloadFormats = {
        'CSV': {
            blob: json2csv(records),
            mime: 'text/csv',
            extension: 'csv',
            select: showTable
        },
        'JSON': {
            blob: JSON.stringify(records, null, '\t') + '\n',
            mime: 'application/json',
            extension: 'json',
            select: showText
        }
    };

    // Instantiate the list of formats
    for (var format in downloadFormats) {
        var a = '<a href="#' + format.split(' ')[0].toLowerCase() +
            '">' + format + '</a>';

        $ff.append('<li>' + a + '</li>');
    };

    // Set a new download file format
    var setActiveDownloadFormat = function (newFormat) {
        var select = downloadFormats[newFormat].select;

        // Transition fade controls
        if ($text.is(':visible')) {
            $text.fadeOut(transitionDuration, function () {
                if (select === showText) {
                    $text.text(downloadFormats[newFormat].blob);
                    showText();
                } else {
                    showTable();
                }
            });
        } else if (select !== showTable) {
            $table.fadeOut(transitionDuration, function () {
                $text.text(downloadFormats[newFormat].blob);
                showText();
            });
        }
    };

    // Respond to user selecting new download format
    $(' li a', $ff).click(function () {
        var format = $(this).text();

        // Highlight the selected format
        $(' li', $ff).removeClass('active');
        $(this).parent().addClass('active');

        // Set new download button text
        $download.text('Download results.' + downloadFormats[format].extension);

        // Update download preview
        setActiveDownloadFormat(format);
    });

    // Set the first format as active
    setActiveDownloadFormat((function () {
        for (var format in downloadFormats)
            return format;
    })());

    $(document).ready(function () {
        /*
         * Generate a human readable version of a table field.
         */
        var humanReadable = function(text) {
            return text.replace(/-/g, ' ');
        };

        /*
         * Add an empty row.
         */
        var addEmptyRow = function (i) {
            var html = '<tr><td>' + i + '</td>';

            for (var j in properties)
                html += '<td></td>';

            $tbody.append(html + '</tr>');
        };

        /*
         * Populate a table row with data from a record.
         */
        var populateRow = function(i, record) {
            var setCell = function($row, index, value) {
                if (value)
                    $(' td:nth-child(' + index + ')', $row).text(value);
            };

            var $row = $(' tr:nth-child(' + i + ')', $tbody);

            for (var j in properties)
                setCell($row, parseInt(j) + 2, record[properties[j]]);
        };

        if (data['No-Of-Records-Matched']) {
            // Generate the header row:
            var header = '<tr><td>0</td>';

            for (var i in properties)
                header += '<td>' + humanReadable(properties[i]) + '</td>';

            $(' thead', $table).append(header + '</tr>');

            // Populate table contents:
            var noRows = Math.max(20, records.length + 5);

            for (var i = 0; i < noRows; i++) {
                addEmptyRow(i + 1);
                if (i < records.length)
                    populateRow((i + 1), records[i]);
            }

            $('.download').show();
        } else {
            // Show the "no results message"
            $noResultsMessage.show();
        }
    });

    $download.click(function () {
        var format = $(' li.active a', $ff).text() || 'CSV'; // Active file format

        var blob = new Blob([downloadFormats[format].blob], {
            type: downloadFormats[format].mime + ';charset=utf-8'
        });

        saveAs(blob, 'results.' + downloadFormats[format].extension);
    });

}());
