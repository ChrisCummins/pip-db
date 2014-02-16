(function () {
    'use strict';

    var $table = $('#table');
    var $text = $('#text');

    var $ff = $('select[name="ff"]');
    var $ih = $('input[name="ih"]');

    $(document).ready(function () {
        var tablify = function() {
            var headerRow = function() {
                var header = '<tr>';

                for (var key in data[0])
                    if (key !== 'id')
                        header += '<td>' + key + '</td>';

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

        $text.text(JSON.stringify(data, null, '\t'));
        tablify();
    });

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

    $ff.change(function () {
        var format = $(' option:selected', this).val().toLowerCase();

        switch (format) {
        case 'json':
            showText();
            break;
        default:
            showTable();
            break;
        }
    });


}());
