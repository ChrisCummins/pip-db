(function () {
    'use strict';

    /*  This work is licensed under Creative Commons GNU LGPL License.

        License: http://creativecommons.org/licenses/LGPL/2.1/
        Version: 0.9
        Author:  Stefan Goessner/2006
        Web:     http://goessner.net/
    */
    function json2xml(o) {
        var toXml = function(v, name, ind) {
            // Escape a string
            var escape = function (text) {
                return String(text).replace(/&/g, '&amp;');
            };

            var xml = "";
            if (v instanceof Array) {
                for (var i = 0, n = v.length; i < n; i++)
                    xml += ind + toXml(v[i], name, ind + "\t") + '\n';
            }
            else if (typeof(v) == "object") {
                var hasChild = false;
                xml += ind + "<";
                xml += Number(name) > -1 ? 'record' : name; // Default
                for (var m in v) {
                    if (m.charAt(0) == "@")
                        xml += " " + m.substr(1) + "=\"" + v[m].toString() + "\"";
                    else
                        hasChild = true;
                }
                xml += hasChild ? ">\n" : "/>";
                if (hasChild) {
                    for (var m in v) {
                        if (m == "#text")
                            xml += v[m];
                        else if (m == "#cdata")
                            xml += "<![CDATA[" + v[m] + "]]>";
                        else if (m.charAt(0) != "@")
                            xml += toXml(v[m], m, ind + "\t") + '\n';
                    }
                    xml += (xml.charAt(xml.length-1)=="\n"?ind:"") + "</";
                    xml += Number(name) > -1 ? 'record' : name; // Default
                    xml += ">";
                }
            }
            else {
                xml += ind + "<" + name + ">" + escape(v) +  "</" + name + ">";
            }
            return xml;
        }, xml="";
        for (var m in o)
            xml += toXml(o[m], m, "") + '\n';
        return xml;
    }

    /*
     * Convert a JSON object into a CSV encoded string.
     */
    var json2csv = function(o, separator) {
        separator = separator || '\t';
        var re = new RegExp(separator, 'g');
        var csv = '';
        var EOL = '\r\n';

        // Add an item to a line
        var appendItemToLine = function(item, line) {

            // Escape a string
            var escape = function (text) {
                return '"' + String(text).replace(re, '\\' + separator) + '"';
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

    // JSON data response map
    var records = data['records'];
    var strippedRecords = (function () { // Blank properties removed
        var s = [], record, r;

        for (var i in records) {
            record = records[i];
            r = {};

            for (var j in record) {
                if (record[j])
                    r[j] = record[j];
            }

            s.push(r);
        };

        return s;
    })();

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

    // Add 'available_at' attributes and filter out 'id'
    for (var i in records) {
        var record = records[i];
        record['available_at'] = 'http://' + location.host + '/r/' + record['id'];
        delete record['id'];
    }

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
            blob: json2csv(strippedRecords),
            mime: 'text/csv',
            extension: '.csv',
            select: showTable
        },
        'JSON': {
            blob: JSON.stringify(records, null, '\t') + '\n',
            mime: 'application/json',
            extension: '.json',
            select: showText
        },
        'XML': {
            blob: json2xml(strippedRecords),
            mime: 'application/xml',
            extension: '.xml',
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
            return key.toUpperCase().replace(/_/g, ' ');
        };

        /*
         * Add an empty row.
         */
        var addEmptyRow = function (i) {
            var html = '<tr><td>' + i + '</td>';

            for (var j = 0; j < dataLength; j++)
                html += '<td></td>';

            $tbody.append(html + '</tr>');
        };

        /*
         * Populate a table row with data from a record.
         */
        var populateRow = function(i, record) {
            var $row = $(' tr:nth-child(' + i + ')', $tbody);
            var j = 1;

            for (var key in record)
                $(' td:nth-child(' + ++j + ')', $row).html(record[key]);
        };

        if (data['no_of_matches']) {
            // Generate the header row:
            var header = '<tr><td>0</td>';

            for (var key in records[0])
                header += '<td>' + humanReadable(key) + '</td>';

            $(' thead', $table).append(header + '</tr>');

            // Populate table contents:
            var dataLength = $(' thead tr td', $table).length;
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
        var format = $(' li.active a', $ff).text(); // Active file format

        var blob = new Blob([downloadFormats[format].blob], {
            type: downloadFormats[format].mime + ';charset=utf-8'
        });

        saveAs(blob, 'results.' + downloadFormats[format].extension);
    });

}());
