(function () {
    'use strict';

    // JSON data response map
    var records = data['Records'];
    var isBlastSearch= data['Query-Terms']['seq'];

    // UI components
    var $download = $('#download');
    var $results = $('.sresults .accordion');
    var $pagination = $('#pagination');
    var $meta = $('.meta-tags');
    var $resultsCount = $(' .results-count', $meta);
    var $noResultsMessage = $('#no-results');

    // Add a new record entry to the results table
    var showRecord = function (record) {

        var getRangeText = function (minProp, maxProp) {
            var min = record[minProp], max = record[maxProp];

            if (min !== undefined && max !== undefined) {
                if (min === max)
                    return min;
                else
                    return min + ' - ' + max;
            } else if (min !== undefined)
                return '> ' + min;
            else if (max !== undefined)
                return '< ' + max;
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
                // TODO: Do we need to indicate that this is a special value?
                return piMajor;
            else // Fallback, in case record has no value
                return ''
        }

        var getRecordHeader = function() {
            var html = '<h3><table><tbody><tr><td>' + record['Protein-Names'] +
                '</td>';

            if (isBlastSearch) {
                html += '<td class="numerical blast">' + record['Raw-Score'] + '</td>';
                html += '<td class="numerical blast">' + record['Expect-Value'] + '</td>';
            }

            return html += '<td class="numerical">' + getPiText() +
                '</td></tr></tbody></table></h3>'
        };

        var getTemperatureText = function() {
            var t = getRangeText('Temperature-Min', 'Temperature-Max');

            if (t !== undefined)
                return t + '&deg;C';
        };

        var getRecordBody = function () {
            var getRecordText = function () {
                var r = function (value, label) {
                    return value !== undefined ?
                        '<span class="property">' + label + ':</span> ' +
                        '<span class="value">' + value + '</span>. '
                        : '';
                };

                var p = function (property, label) {
                    return r(record[property], label);
                };

                return '<p>' +
                    p('Source', 'Source') +
                    p('Location', 'Organ and/or Subcellular location') +
                    p('EC', 'Enzyme Commission Number') +
                    p('No-Of-Iso-Enzymes', 'Number of Iso Enzymes') +
                    p('Method', 'Experimental Method') +
                    r(getTemperatureText(), 'Temperature') +
                    r(getRangeText('MW-Min', 'MW-Max'), 'Molecular Weight') +
                    p('Subunit-No', 'Subunit Number') +
                    p('Subunit-Mw', 'Subunit Molecular Weight') +
                    p('Notes', 'Notes') +
                    '</p>';
            };

            var getRecordLink = function () {
                return '<div class="more pull-right"><a href="' + record['Available-At'] +
                    '">See more information &gt;&gt;</a></div>'
            };

            var getBlastText = function () {
                return isBlastSearch ? '<h5>BLAST+ sequence alignment:</h5><div class="alignment">' +
                    '<table><tbody><tr>' +
                    '<td class="type">Query:</td><td class="sequence">' + record['Query-Sequence'] + '</td></tr>' +
                    '<tr><td class="type">Subject:</td><td class="sequence">' + record['Subject-Sequence'] +
                    '</td></tr></tbody></table></div>' : '';
            };

            return '<div>' +
                getBlastText() +
                getRecordText() +
                getRecordLink() +
                '</div>';
        };

        $results.append(getRecordHeader() + getRecordBody());
    };

    if (data['No-Of-Records-Returned']) {
        // Populate the meta header if we have results
        $resultsCount.text((function () {
            var resultsText = data['No-Of-Records-Matched'] === 1 ?
                '1 result ' : data['No-Of-Records-Matched'] + ' results';

            var matched = data['No-Of-Records-Matched'];
            var returned = data['No-Of-Records-Returned'];
            var start = data['Returned-Records-Starting-At']

            var paginationText = returned < matched ?
                (start ? ' and showing results ' + start + ' - ' + (start + returned)
                 : ' and showing the first ' + returned) : '';

            return 'Found ' + resultsText + ' of a possible ' +
                data['No-Of-Records-Searched'] + paginationText + '...';
        })());

        // Populate the table
        for (var i in records)
            showRecord(records[i]);

        $results.accordion({
            collapsible: true,
            active: false,
            animate: 150,
            heightStyle: 'content'
        });

        // Table header
        var html = '<div class="head"><table><thead><tr><td>Protein Names</td>';

        if (isBlastSearch) {
            html += '<td class="numerical blast">Score</td>';
            html += '<td class="numerical blast">Evalue</td>';
        }

        html += '<td class="numerical">pI</td></tr></thead></table></div>';

        $results.prepend(html);

        $download.show(); // Show the download button

        // Pagination links:
        var max_page_links = 10;
        var results_per_page = data['Max-No-of-Returned-Records'];
        var results_url = new String(window.location).replace(/&start=\d+/, '');
        var start = data['Returned-Records-Starting-At'];
        var html = '<div class="row" style="margin: 20px 0;">' +
                   '<div class="col-lg-12" style="text-align:center;">' +
                   '<div id=#pagination class="btn-group" style="margin: 0 auto;">';

        var page_count = Math.ceil(data['No-Of-Records-Matched'] / results_per_page);
        var current_page = Math.floor(start / results_per_page) + 1;
        var start_page = Math.max(1, current_page - (max_page_links / 2));
        var end_page = Math.min(page_count + 1, start_page + max_page_links);

        var get_results_page_url = function(n) {
          if (n > 1)
            return results_url + '&start=' + (n - 1) * results_per_page;
          else
            return results_url;
        };

        var pagination_button = function(contents, index, disabled) {
          return '<a class="page-ref btn btn-success' +
            (disabled ? ' disabled' : '') +
            '" href="' + get_results_page_url(index) + '">' +
            contents + "</a>";
        }

        if (current_page > 1)
          html += pagination_button('&laquo', 1);

        for (var i = start_page; i < end_page; i++)
          html += pagination_button(i, i, i == current_page)

        if (end_page < page_count)
          html += pagination_button('&raquo', page_count);

        html += '</div></div></div>'
        $results.append(html);

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
