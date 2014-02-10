/*
 * Site-common JavaScript functionality.
 *
 * This script is sourced from every HTML page.
 */

(function () {
    'use strict';

    /*
     * Avoid `console` errors in browsers that lack a console.
     */
    var method,
        noop = function () {},
        methods = [
            'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
            'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
            'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
            'timeStamp', 'trace', 'warn'
        ],
        length = methods.length,
        console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }

    /*
     * The search bar always grabs page focus.
     */
    var value = $("#q").val();
    $("#q").focus().val('').val(value);
}());
