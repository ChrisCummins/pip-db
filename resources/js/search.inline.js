(function () {
    'use strict';

    // JSON data response map
    var records = data['records'];

    // The base URL component for results
    var resultsUrlPrefix = '/r/'

    // UI components
    var $download = $('#download');
    var $table = $('.sresults table');
    var $tbody = $(' tbody', $table);
    var $pagination = $('#pagination');
    var $meta = $('.meta-tags');
    var $resultsCount = $(' .results-count', $meta);
    var $noResultsMessage = $('#no-results');

    /*
     * SEARCH RESULTS:
     *
     * Link each result to its corresponding record page.
     */
    var attachTableListeners = function () {
        $(' tr', $tbody).click(function () {
            window.location = resultsUrlPrefix + $(this).attr('data-id');
        });
    };

    // Add a new record entry to the results table
    var addRecordRow = function (record) {

        // Get the HTML for a table cell
        var getCell = function (name, value) {
            return '<td class="' + name + '">' + value + '</td>';
        };

        // Get the HTML for a table cell from a record property
        var getRecordCell = function (property) {
            return getCell(property, record[property]);
        };

        // Get the HTML for a pI table cell from a record
        var getPiText = function () {
            /*
             * We allow for some flexibility in displaying isoelectric points. We
             * will try first to show an exact value, else a range of values, or
             * just an individual result within that range.
             */
            var pi      = record['pi'];
            var piMin   = record['pi_range_min'];
            var piMax   = record['pi_range_max'];
            var piMajor = record['pi_major'];

            if (pi)             return pi;
            if (piMin && piMax) return piMin + ' - ' + piMax;
            if (piMin)          return '> ' + piMin;
            if (piMax)          return '< ' + piMax;
            if (piMajor)        return piMajor + 'm';
        }

        var html = '<tr data-id="' + record['id'] + '">';

        html += getRecordCell('name');
        html += getRecordCell('source');
        html += getRecordCell('organ');
        html += getCell('pi', getPiText());

        $tbody.append(html + '</tr>');
    };

    if (data['no_of_matches']) {
        // Populate the meta header if we have results
        $resultsCount.text((function () {
            var resultsText = data['no_of_matches'] === 1 ?
                '1 result ' : data['no_of_matches'] + ' results';

            var returnedText =
                data['no_of_returned_records'] < data['no_of_matches'] ?
                ' and returned the first ' + data['no_of_returned_records'] : '';

            return 'Found ' + resultsText + ' of a possible ' +
                data['no_of_records'] + returnedText + '...';
        })());

        // Populate the table
        for (var i in records)
            addRecordRow(records[i]);

        // Show the table
        $table.show();

        // Add link handlers to record page
        attachTableListeners();
    } else {
        // Show the "no results" message
        $noResultsMessage.show();
    }

    $(document).ready(function () {
        // Set the Download link
        $download.attr('href', (function () {
            var downloadPageUrl = '/d'
            var href = window.location.href;
            var queryString = href.slice(href.indexOf('?') + 1);

            return downloadPageUrl + '?' + queryString;
        })());
    });

}());
