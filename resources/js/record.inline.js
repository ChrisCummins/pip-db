$(document).ready(function () {
    'use strict';

    /*
     * Lookup a property by key. The key must be a string which matches
     * a data-key tag. The property's value is returned as a string. Example:
     *
     *   get_property("name")
     *     -> "Alkaline phosphatase"
     *
     * If a property matching the key is not found, undefined is
     * returned. This is a list lookup, with linear O(n) time
     * performance.
     */
    var get_property = function (key) {
        var value;

        $('#properties .property').each(function (index) {
            var property = $(this).attr('data-key');

            if (key === property) {
                value = $(this).children('.value').text();
                return false;           // Break loop
            }
        });

        return value;
    };

    /*
     * REFERENCE THIS PAGE:
     */
    $('#reference').html('pip-db (2014). <i>' + get_property("name") +
                         '</i>, Protein Isoelectric point Database. ' +
                         'Available from: &lt;<a href="' + window.location +
                         '"" class=\"url\">' + window.location +
                         '</a>&gt;<br/>[Cited ' +
                         moment().format('Do MMMM, YYYY') + '].');

    /*
     * RECORD ADDED:
     */
    $('#date-added').html('Added ' +
                          moment($('#date-added').attr('data-date')).fromNow() +
                          '.');

});
