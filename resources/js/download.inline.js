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
            var xml = "";
            if (v instanceof Array) {
                for (var i=0, n=v.length; i<n; i++)
                    xml += ind + toXml(v[i], name, ind + "\t") + '\n';
            }
            else if (typeof(v) == "object") {
                var hasChild = false;
                xml += ind + "<" + name;
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
                    xml += (xml.charAt(xml.length-1)=="\n"?ind:"") + "</" + name + ">";
                }
            }
            else {
                xml += ind + "<" + name + ">" + v.toString() +  "</" + name + ">";
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
        for (var j in data[0])
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
    var $table = $('#table');
    var $tbody = $(' tbody', $table);
    var $text = $('#text');

    var $ff = $('select[name="ff"]');
    var $ih = $('input[name="ih"]');

    // Add 'available_at' attributes to text
    for (var id in data)
        data[id]['available_at'] = 'http://' + location.host + '/r/' + id;

    // Generate text formatted data
    var textFormats = {
        'json': JSON.stringify(data, null, '\t'),
        'xml': json2xml(data)
    };

    var showTable = function () {
        $table.show();
        $text.hide();
        $ih.prop('disabled', false);
    };

    var showText = function () {
        $table.hide();
        $text.show();
        $ih.prop('disabled', true);
    };

    var setVisibleFormat = function(format) {
        switch (format) {
        case 'json':
            $text.text(textFormats['json']);
            showText();
            break;
        case 'xml':
            $text.text(textFormats['xml']);
            showText();
            break;
        default:
            showTable();
            break;
        }
    }

    $ff.change(function () {
        var format = $(' option:selected', this).val().toLowerCase();

        setVisibleFormat(format);
    });

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

            for (var key in record) {
                if (key !== 'id')
                    $(' td:nth-child(' + ++j + ')', $row).html(record[key]);
            }
        };

        // Generate the header row:
        var header = '<tr><td>0</td>';

        for (var key in data[0]) {
            if (key !== 'id')
                header += '<td>' + humanReadable(key) + '</td>';
        }

        $(' thead', $table).append(header + '</tr>');

        var dataLength = $(' thead tr td', $table).length;

        // Populate table contents:
        for (var i = 0; i < data.length + 10; i++) {
            addEmptyRow(i);
            if (i < data.length)
                populateRow(i, data[i]);
        }
    });

}());
