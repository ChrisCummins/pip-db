$(document).ready(function () {
    'use strict';

    /*
     * SEARCH RESULTS:
     *
     * Link each result to its corresponding record page.
     */
    $('.sresults table tr').click(function () {
        window.location = '/record/' + $(this).attr('data-id');
    });

    /*
     * PAGINATION:
     *
     * Add hyperlinks to pagination links.
     */
    var results_per_page = $('#pagination').attr('data-results-per-page'),
        url = window.location.toString().replace(/&start=\d+/, ''),
        get_results_page_url = function (n) {
            if (n > 1) { return url + '&start=' + (n - 1) * results_per_page; }
            return url;
        };

    $('#pagination a.page-ref').each(function (index) {
        $(this).attr('href', get_results_page_url($(this).attr('data-page')));
    });

});
