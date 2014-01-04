$(document).ready(function() {
  /*
   * REFERENCE THIS PAGE:
   */
  $('#reference').html('Protein Isoelectric point Database (2014) <i>' +
                       window.document.title.replace(/pip-db /, '') +
                       '</i>, pip-db. Available from: &lt;' +
                       '<span class=\"url\">' +
                       window.location + '</span>&gt;<br/>[Cited ' +
                       moment().format('Do MMMM, YYYY') + '].');

  /*
   * RECORD ADDED:
   */
  $('#date-added').html('Added ' +
                        moment($('#date-added').attr('data-date')).fromNow() +
                        '.');

});
