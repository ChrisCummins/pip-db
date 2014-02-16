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
     * PIP-DB DOWNLOADS PAGE
     */
    var $table = $('#table');
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
        var tablify = function() {
            var headerRow = function() {
                var humanReadable = function(text) {
                    return key.toUpperCase().replace('_', ' ');
                };
                var header = '<tr>';

                for (var key in data[0])
                    if (key !== 'id')
                        header += '<td>' + humanReadable(key) + '</td>';

                $(' thead', $table).append(header + '</tr>');
            };

            var body = function() {
                var row = function(record) {
                    var html = '<tr>';

                    for (var key in record) {
                        if (key !== 'id')
                            html += '<td>' + record[key] + '</td>';
                    }

                    return html + '</tr>';
                };

                for (var record in data)
                    $(' tbody', $table).append(row(data[record]));
            };

            headerRow();
            body();
        };

        tablify();
    });

}());
