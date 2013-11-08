<?php

function get_header( $inline_search = false, $value = null,
                     $login_only = false ) {

	echo <<<EOF
      <!-- Fixed navbar -->
    <div class="navbar navbar-fixed-top
EOF;

    if ($login_only == true) {
        echo " navbar-invisible";
    } else {
        echo " navbar-default";
    }

    echo <<<EOF
">
        <div class="navbar-header">

          <!-- Collapse button -->
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>

          <!-- The logo -->
          <a class="navbar-brand" href="results.php">pip-db</a>

        </div> <!-- /.navbar-header -->

        <div class="navbar-collapse collapse">
EOF;

	if ( true == $inline_search ) {
		echo <<<EOF
          <div class="col-sm-7 col-md-7 navbar-search">

            <form class="navbar-form" role="search">
              <div class="input-group">

                <!-- Search bar -->
                <input id="s" name="s" type="text" class="form-control"
                       value="
EOF;

		$value = (string) $value;

		if ( '' !== $value ) {
			echo $value;
		}

        	echo <<<EOF
">

                <!-- Search button -->
                <div class="input-group-btn">
                  <a href="/search.php" class="btn btn-success" type="submit">
                    Search
                  </a>
                </div>
              </div>
            </form>

          </div> <!-- /.col-md-7 -->

          <!-- Advanced button -->
          <ul class="nav navbar-nav">
            <li>
              <div class="navbar-button-group">
                <div class="navbar-button">
                  <a href="/advanced.php" class="btn btn-primary">
                    Advanced
                  </a>
                </div>
              </div>
            </li>
          </ul> <!-- /.navnar-nav -->
EOF;
	}

    echo <<<EOF
          <!-- Login button -->
          <ul class="nav navbar-nav navbar-right">
            <li><a href="/login.php">Login</a></li>
          </ul> <!-- /.navbar-right -->

        </div><!--/.nav-collapse -->
      </div> <!-- /.navbar -->
EOF;

}
