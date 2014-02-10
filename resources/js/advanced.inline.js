$(function () {
  'use strict';

  var hashDiff = function(h1, h2) {
    var diff = {}, k;

    for (k in h2) {
      if (h1[k] !== h2[k])
        diff[k] = h2[k];
    }

    return diff;
  };

  var hashForm = function(form) {
    var a = form.serializeArray(), hash = {};

    a.forEach(function(element) {
      hash[element.name] = element.value;
    });

    return hash;
  };

  var $form = $('#as');
  var startItems = hashForm($form);

  $form.submit(function(e) {
    e.preventDefault();

    var itemsToSubmit = hashDiff(startItems, hashForm($form));

    window.location = $form.attr('action') + '?' + $.param(itemsToSubmit);
  });

});
