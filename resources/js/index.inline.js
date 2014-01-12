$(function() {
  $('#q').focus();
});

$('#q').focus(function() {
  $('#qp').addClass('active');
});

$('#q').blur(function() {
  $('#qp').removeClass('active');
});
