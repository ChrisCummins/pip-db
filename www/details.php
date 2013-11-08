<!doctype html>
<html lang="en">

  <?php require_once( 'functions.php' ); ?>

  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>pip-db Alkaline phosphatese</title>

    <link rel="stylesheet" href="css/styles.css"/>
  </head>

  <body>

    <!-- Content wrapper -->
    <div id="wrap">

      <?php get_header( true, 'Alkaline' ); ?>

      <div class="container">

        <!-- Page title -->
        <div class="page-title">
          <!-- Download results -->
          <div class="download">
            <button class="btn btn-warning">Download</button>
          </div>

          <!-- Title of record page -->
          <h3>Alkaline phosphatese</h3>
          <hr>
        </div> <!-- /.page-title -->

        <div id="details">

          <div class="row">
            <div class="col-md-8">
              <table class="table table-striped table-bordered">

                <!-- Table body -->
                <tbody>

                  <tr> <!-- Detail -->
                    <td>Name:</td>
                    <td>Alkaline phosphatese</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Alternative name:</td>
                    <td>Alkaline phosphatese</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Source:</td>
                    <td>Human</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Location:</td>
                    <td>Placenta</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>pI:</td>
                    <td>4.6</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Molecular weight:</td>
                    <td>116000 - 116000</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Sub unit no:</td>
                    <td>2</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Sub unit MW:</td>
                    <td>58000</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Iso Enzymes:</td>
                    <td>1 - 1</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Method:</td>
                    <td>Isoelectric focusing</td>
                  </tr>

                  <tr> <!-- Detail -->
                    <td>Temperature:</td>
                    <td>22 C</td>
                  </tr>

                </tbody>
              </table>

            </div> <!-- /.col-md-8 -->

            <div class="col-md-4">

              <!-- External links section -->
              <div class="extern">
                <h4>External Links</h4>

                <ul class="extern">
                  <li><button class="btn btn-info">Full Text</button></li>
                  <li><button class="btn btn-info">PubMed</button></li>
                  <li><button class="btn btn-info">Species Taxonomy</button></li>
                  <li><button class="btn btn-info">Protein Sequence</button></li>
                </ul>
              </div> <!-- /.extern -->

              <!-- Reference text section -->
              <div class="ref">
                <h4>Reference this page</h4>

                <blockquote>pip-db. (2012) <i>Protein Isoelectric Point Database</i>,
                  Alkaline phosphatese. Available from:
                  &lt;http://www.pip-db.com/r?id=12023&gt;. [Cited 10 July 2013].
                </blockquote>
              </div> <!-- /.ref -->

            </div> <!-- /.col-md-4 -->

          </div> <!-- /.row -->

          <div class="row">
            <div class="col-md-12">
              <!-- Record metadata -->
              <div class="meta-holder">
                <ul class="meta">
                  <li>Added on March 10th 2012.</li>
                  <li>Last edited 6 hours ago.</li>
                </ul>
              </div>
            </div> <!-- /.col-md-12 -->
          </div> <!-- /.row -->

        </div> <!-- /#details -->

      </div> <!-- /.container -->
    </div> <!-- /.wrap -->

    <?php get_footer(); ?>

    <script src="//code.jquery.com/jquery.js"></script>
    <script src="js/bootstrap.js"></script>
  </body>

  <script>
    $(document).ready(function(){
      /*
       * Search results link to details page:
       */
      $('div.sresults table tbody tr').click(function(){
        window.location = $(this).attr('url');
      });
    });
  </script>

</html>
