(function () {
    'use strict';

    // The base URL component for results
    var resultsUrlPrefix = '/r/'

    var $download = $('#download');
    var $table = $('.sresults table');
    var $tbody = $(' tbody', $table);
    var $pagination = $('#pagination');

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

    // Populate the table
    for (var i in data)
        addRecordRow(data[i]);

    // Add link handlers to record page
    attachTableListeners();

    /*
     * PAGINATION:
     *
     * Add hyperlinks to pagination links.
     */
    var results_per_page = $pagination.attr('data-results-per-page'),
        url = window.location.toString().replace(/&start=\d+/, ''),
        get_results_page_url = function (n) {
            if (n > 1) { return url + '&start=' + (n - 1) * results_per_page; }
            return url;
        };

    $('#pagination a.page-ref').each(function (index) {
        $(this).attr('href', get_results_page_url($(this).attr('data-page')));
    });

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
