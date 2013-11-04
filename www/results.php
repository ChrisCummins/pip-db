<!doctype html>
<html lang="en">

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db "Alkaline"</title>

    <link rel="stylesheet" href="css/styles.css"/>
    <link rel="stylesheet" href="css/bootstrap.css">
  </head>

  <body>

    <!-- Header -->
    <div class="header grey">
      <div class="search inline">
        <a href="index.php"><h1 class="logo">pip-db</h1></a>
        <input id="search-text" type="text" value="Alkaline.">
        <a href="results.php">
          <button id="s" class="btn btn-success">Search</button>
        </a>
        <a href="advanced.php">
          <button id="advsearch" class="btn btn-info">Advanced</button>
        </a>
      </div>
    </div>

    <div class="container">

      <!-- Page title -->
      <div class="page-title">
        <!-- Download results -->
        <div class="download">
          <button class="btn btn-warning">Download</button>
        </div>

        <!-- Number of search results -->
        <div class="info">
          Found 122 results...
        </div>
        <hr>
      </div> <!-- /.page-title -->

      <div class="sresults">

      </div> <!-- /.sresults -->

    </div>

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

</html>
