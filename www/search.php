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

            <a id="search-browse" href="advanced.php" class="btn btn-primary">
              Advanced
            </a>

            <a id="search-submit" href="results.php" class="btn btn-success">
              Search
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
