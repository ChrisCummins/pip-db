<!doctype html>
<html lang="en">

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db</title>

    <link rel="stylesheet" href="css/styles.css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
  </head>

  <body>

    <div class="header">
      <div class="search inline">
        <div class="ubar">
          <div class="login">
            <button class="btn">Login</button>
          </div>
        </div>
      </div>
    </div>

    <div class="container">
      <div class="search full">
        <h1 class="logo">pip-db</h1>

        <!-- Search form -->
        <form method="GET">
          <!-- le textbox -->
          <input id="in" type="text">

          <!-- buttons -->
          <div class="btn-row">
            <a href="advanced.php">
              <button id="search-browse" class="btn btn-info">Advanced</button>
            </a>

            <a href="results.html">
              <button id="search-submit" class="btn btn-success">Search</button>
            </a>
          </div> <!-- /.button-row -->
        </form>

      </div>
    </div>

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

</html>
