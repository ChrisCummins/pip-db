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

  /*
   * Return a subset of values where each item is different from the
   * default. If no default is given for a value, then return it.
   */
  var stripDefaultValues = function(values, defaults) {
    var items = {}, length = 0;

    values.forEach(function(e) {
      if (e.value !== defaults[e.name]) {
        items[e.name] = e.value;
        length++;
      }
    });

    return {length: length, values: items};
  };

  $searchForms = $('form[action="/s"]');

  /*
   * Append the advanced button value to the form values.
   */
  $(':submit', $searchForms).click(function() {
    if ($(this).attr('value') === 'a') {
      $(this).closest('form').append($("<input type='hidden'>").attr({
        name: $(this).attr('name'),
        value: $(this).attr('value')
      }));
    }
  });

  $searchForms.submit(function(e) {
    e.preventDefault();

    var items = stripDefaultValues($(this).serializeArray(), {
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
    });

    if (items.length) // Only submit form if we have some unique values
      window.location = $(this).attr('action') + '?' + $.param(items.values);
  });

}());
