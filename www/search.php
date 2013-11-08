<!doctype html>
<html lang="en">

  <?php require_once( 'functions.php' ); ?>

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db</title>

    <link rel="stylesheet" href="css/styles.css"/>
  </head>

  <body>

    <!-- Content wrapper -->
    <div id="wrap">

      <?php get_header( false, '', true ); ?>

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

    <?php get_footer(); ?>

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

  <script>
    $(function() {
      $('input#in').focus();
    });
  </script>

</html>
