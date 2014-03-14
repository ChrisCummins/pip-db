(function () {
    'use strict';

    // JSON data response map
    var records = data['Records'];

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
            window.location = $(this).attr('data-url');
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
            return getCell(property, record[property] || '');
        };

        // Get the text for a pI table cell from a record
        var getPiText = function () {
            /*
             * We allow for some flexibility in displaying isoelectric points. We
             * will try first to show an exact value, else a range of values, or
             * just an individual result within that range.
             */
            var piMin   = record['pI-Min'];
            var piMax   = record['pI-Max'];
            var piMajor = record['pI-Major-Component'];

            if (piMin && piMax) {
                if (piMin === piMax)
                    return piMin;
                else
                    return piMin + ' - ' + piMax;
            } else if (piMin)
                return '> ' + piMin;
            else if (piMax)
                return '< ' + piMax;
            else if (piMajor)
                return piMajor + 'm';
            else // Fallback, in case record has no value
                return ''
        }

        var html = '<tr data-url="' + record['Available-At'] + '">';

        html += getRecordCell('Protein-Names');
        html += getRecordCell('Source');
        html += getRecordCell('Location');
        html += getCell('pI', getPiText());

        $tbody.append(html + '</tr>');
    };

    if (data['No-Of-Records-Matched']) {
        // Populate the meta header if we have results
        $resultsCount.text((function () {
            var resultsText = data['No-Of-Records-Matched'] === 1 ?
                '1 result ' : data['No-Of-Records-Matched'] + ' results';

            var returnedText =
                data['No-Of-Records-Returned'] < data['No-Of-Records-Matched'] ?
                ' and returned the first ' + data['No-Of-Records-Returned'] : '';

            return 'Found ' + resultsText + ' of a possible ' +
                data['No-Of-Records-Searched'] + returnedText + '...';
        })());

        // Populate the table
        for (var i in records)
            addRecordRow(records[i]);

        // Show the table
        $table.show();

        // Add link handlers to record page
        attachTableListeners();

        $download.show(); // Show the download button
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
