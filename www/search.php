<!doctype html>
<html lang="en">

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db</title>

    <link rel="stylesheet" href="css/styles.css"/>
  </head>

  <body>

    <!-- Content wrapper -->
    <div id="wrap">

      <!-- Top of the page header -->
      <div class="header">
        <div class="search inline">
          <div class="ubar">
            <div class="ubar-inner">
              <a id="login" href="login.php">Login</a>
            </div>
          </div>
        </div>
      </div>

      <!-- Main body of page -->
      <div class="container">
        <div class="search full">
          <h1 class="logo">pip-db</h1>

          <!-- Search form -->
          <form method="GET">
            <!-- le textbox -->
            <input id="in" type="text">
          </form>

          <!-- buttons -->
          <div class="btn-row">
            <a href="advanced.php">
              <button id="search-browse" class="btn btn-info">Advanced</button>
            </a>

            <a href="results.php">
              <button id="search-submit" class="btn btn-success">Search</button>
            </a>
          </div> <!-- /.button-row -->

        </div> <!-- /.search-full -->
      </div> <!-- /.container -->
    </div> <!-- /.wrap -->

    <!-- Bottom of the page footer -->
    <div id="footer">

      <div class="left">
        <div class="footer-inner">
          <a href="#">About</a>
          <a href="#">Contact</a>
        </div>
      </div>

      <div class="right">
        <div class="footer-inner">
          <a href="#">Terms</a>
          <a href="#">Privacy</a>
        </div>
      </div>

    </div> <!-- /.footer -->

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

</html>
