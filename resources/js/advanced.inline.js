$(function () {
  'use strict';

  /*
   * Return a subset of values where each item is different from the
   * default. If no default is given for a value, then return it.
   */
  var stripDefaultValues = function(values, defaults) {
    var items = {}, length = 0;

    values.forEach(function(e) {
      if (defaults[e.name] !== undefined) {
        /* Test whether value is same as default value */
        if (e.value !== defaults[e.name]) {
          items[e.name] = e.value;
          length++;
        }
      } else {
        items[e.name] = e.value;
      }
    });

    return {length: length, values: items};
  };

  var $form = $('#as');

  $form.submit(function(e) {
    e.preventDefault();

    var items = stripDefaultValues($form.serializeArray(), {
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
      window.location = $form.attr('action') + '?' + $.param(items.values);
  });

});
