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
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

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

    /*
     * Return a subset of values where each item is different from the
     * default. If no default is given for a value, then return it.
     */
    var stripDefaultValues = function (values, defaults) {
        var items = {}, itemsLength = 0;

        values.forEach(function (e) {
            if (e.value !== defaults[e.name]) {
                items[e.name] = e.value;
                itemsLength++;
            }
        });

        return {length: itemsLength, values: items};
    };

    /*
     * Perform the default value checks from stripDefaultValues but
     * simply return a boolean for whether or not the array did contain
     * a unique value. This executes much faster since it exits early,
     * with worst case times O(n).
     */
    var containsUniqueValues = function (values, defaults) {
        try {
            values.forEach(function (e) {
                if (e.value !== defaults[e.name]) {
                    throw new SuccessException;
                }
            });
        } catch (e) {
            return true;
        }

        return false;
    };

    var $searchForms = $('form[action="/s"]');
    var searchFormDefaults = {
        'q': '',
        'q_eq': '',
        'q_any': '',
        'q_ne': '',
        'q_s': '',
        'q_l': '',
        'ec1': '',
        'ec2': '',
        'ec3': '',
        'ec4': '',
        'pi_l': '',
        'pi_h': '',
        'mw_l': '',
        'mw_h': '',
        'm': 'Any',
        't_l': '',
        't_h': ''
    };

    /*
     * Append the advanced button value to the form values.
     */
    $(':submit', $searchForms).click(function () {
        var $form = $(this).closest('form');

        if ($(this).attr('value') === 'a') {
            $form.append($("<input type='hidden'>").attr({
                name: $(this).attr('name'),
                value: $(this).attr('value')
            }));
        }
    });

    $searchForms.submit(function (e) {
        e.preventDefault();

        var items = stripDefaultValues($(this).serializeArray(),
                                       searchFormDefaults);

        if (items.length) { // Only submit form if we have some unique values
            window.location = $(this).attr('action') +
                '?' + $.param(items.values);
        }
    });

    /*
     * Checks whether a search form has been filled in with data, and
     * activates the submit button if so.
     */
    var activateSubmitIfFormFilled = function ($form) {
        var $submit = $(' button[name="a"][value="s"]', $form);

        if (containsUniqueValues($form.serializeArray(), searchFormDefaults)) {
            $submit.removeClass('disabled'); // Enable button
        } else {
            $submit.addClass('disabled'); // Disable button
        }
    }

    // Bind form validation to text input keystrokes
    $($searchForms).bind('input propertychange', function (e) {
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    // Bind form validation to dropdown selections
    $(' select', $searchForms).change(function (e) {
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    // Validate form on load (in case of preloaded criteria)
    activateSubmitIfFormFilled($searchForms);

}());
