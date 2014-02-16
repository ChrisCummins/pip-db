(function () {
  'use strict';

  var emailRe = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  var $submit = $('button[name="action"][value="login"]');
  var $email = $('input[name="user"]');
  var $pass = $('input[name="pass"]');

  var setError = function ($input) {
    $input.parent().addClass('has-error');
    $input.parent().removeClass('has-success');
  };

  var setSuccess = function ($input) {
    $input.parent().removeClass('has-error');
    $input.parent().addClass('has-success');
  };

  var clearState = function () {
    $(this).parent().removeClass('has-error');
    $(this).parent().removeClass('has-success');
  };

  $email.focusin(clearState);
  $pass.focusin(clearState);
  $email.bind('input propertychange', clearState);
  $pass.bind('input propertychange', clearState);

  var formIsValid = function () {
    var e = (function () {
      if (emailRe.test($email.val())) {
        return true;
      } else {
        setError($email);
        return false;
      }
    })();

    var p = (function () {
      if ($pass.val().trim() !== '') {
        return true;
      } else {
        setError($pass);
        return false;
      }
    })();

    return e && p;
  };

  var getLoginFormData = function () {
    return 'user=' + $email.val() + '&pass=' + $pass.val() + '&action=login';
  };

  var clearForm = function() {
    $email.val('');
    $pass.val('');
  };

  $submit.click(function (e) {
    e.preventDefault(); // Don't submit form

    if (formIsValid()) {
      $('.alert').hide(); // Hide existing messages

      $.ajax({
        type: 'POST',
        url: '/login',
        data: getLoginFormData(),
        success: function(data, status, jqxhr) {
          /* Login succeeded: */
          clearForm();                      // Clear form contents
          $('button').addClass('disabled'); // Lock down form
          $('#200').show();                 // Show success message

          setTimeout(function () { // Redirect user
            window.location = jqxhr.getResponseHeader('Location');
          }, 500);
        },
        error: function(jqxhr, status, error) {
          if (error === 'Unauthorized') {
            /* Login details were incorrect: */
            $('#403').show();
            clearForm();
            setError($email);
            setError($pass);
          } else {
            /* Miscellaneous error: */
            $('#500').show();
          }
        }
      });
    }
  });

}());
