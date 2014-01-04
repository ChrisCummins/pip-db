$(document).ready(function() {
  /*
   * SEARCH RESULTS:
   *
   * Link each result to its corresponding record page.
   */
  $('.sresults table tr').click(function() {
    window.location = '/record/' + $(this).attr('data-id');
  });
});
