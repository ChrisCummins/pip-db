<!doctype html>
<html lang="en">

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db Sign In</title>

    <link rel="stylesheet" href="css/styles.css"/>
  </head>

  <style>
    body {
      background: #eee;
      padding-top: 40px;
      padding-bottom: 40px;
    }
  </style>

  <body>

      <!-- Main body of page -->
      <div class="container">

	 <form method="post" action="/search.php" class="form-signin">
          <h2 class="form-signin-heading">Login</h2>
          <input name="user" type="text" class="form-control" placeholder="Email address" required autofocus>
          <input name="pass" type="password" class="form-control" placeholder="Password" required>
          <label class="checkbox">
            <input type="checkbox" value="remember-me"> Remember me
          </label>
          <div style="display: table; width: 100%;">
            <div style="display: table-cell; width: 50%; padding-right: 8px;">
              <button class="btn btn-lg btn-primary btn-block"
                      type="submit">
                Register
              </button>
            </div>
            <div style="display: table-cell; width: 50%; padding-left: 8px;">
              <button class="btn btn-lg btn-success btn-block"
                      type="submit">
                Sign in
              </button>
            </div>
          </div> <!-- /div -->
        </form>

      </div> <!-- /.container -->

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

</html>
