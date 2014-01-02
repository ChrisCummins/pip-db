(ns pip-db.views.index
  (:use [pip-db.views.layout]))

(defn index []
  (common ""
          [:div.row
           [:div.search.col-md-8.col-md-offset-2.text-center
            [:h1.logo "pip-db"]
            [:form {:method "GET" :action "/s"}
             [:div#qp
              [:table {:cellspacing "0" :cellpadding "0"}
               [:tbody
                [:tr
                 [:td [:input#q {:name "q" :type "text" :autocomplete "off"}]]
                 [:td#h [:strong "?"]]]]]]

             [:div.btn-row
              [:a#search-browse.btn.btn-primary {:href "/advanced"} "Advanced"]
              [:button#search-submit.btn.btn-success "Search"]]]]]

          [:script "
    $(function() {
      $('#q').focus();
    });

    $('#q').focus(function() {
      $('#qp').addClass('active');
    });

    $('#q').blur(function() {
      $('#qp').removeClass('active');
    });"]))
