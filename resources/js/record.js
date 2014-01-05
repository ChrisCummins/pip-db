$(document).ready(function() {

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
  var get_property = function(key) {
    var value = undefined;

    $('#properties .property').each(function (index) {
      var property = $(this).attr('data-key');

      console.log(property + ', KEY: ' + key);

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
                       'Available from: &lt;<span class=\"url\">' +
                       window.location + '</span>&gt;<br/>[Cited ' +
                       moment().format('Do MMMM, YYYY') + '].');

  /*
   * RECORD ADDED:
   */
  $('#date-added').html('Added ' +
                        moment($('#date-added').attr('data-date')).fromNow() +
                        '.');

});
