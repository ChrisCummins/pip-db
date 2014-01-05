$(function() {

  function hashDiff(h1, h2){
    var diff = {};

    for (k in h2) {
      if (h1[k] !== h2[k])
        diff[k] = h2[k];
    }

    return diff;
  }

  function convertSerializedArrayToHash(a){
    var hash = {};

    a.forEach(function(element) {
      hash[element.name] = element.value;
    })

    return hash;
  }

  var $form = $('#as');
  var startItems = convertSerializedArrayToHash($form.serializeArray());

  $form.submit(function(event) {

    event.preventDefault();

    var currentItems = convertSerializedArrayToHash($form.serializeArray());
    var itemsToSubmit = hashDiff(startItems, currentItems);

    window.location = $form.attr('action') + '?' + $.param(itemsToSubmit);
  });

});
