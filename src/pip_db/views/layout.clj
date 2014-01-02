(ns pip-db.views.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]
        [pip-db.views.components]))

(defn common [title & body]
  (html5 {:lang "en" :class "no-js"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:title "pip-db " title]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:meta {:name "msapplication-tooltip"
            :content "Protein Isoelectric Point Database."}]
    (include-css "/css/styles.css")]

   [:body
    (include-navbar)
    [:div#wrap [:div.container body]]
    (include-footer)

    (include-js "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
    [:script "window.jQuery || "
     "document.write('<script src=\"/js/jquery-1.10.2.min.js\"><\\/script>');"]
    (include-js "/js/bootstrap-3.0.1.min.js"
                "/js/main.js")]))

(defn not-found []
  (common "Page Not Found"
          [:div.row
           [:div.col-lg-12.text-center
            [:div.jumbotron.errortron
             [:h1 "404 :("]
             [:p "Sorry, I couldn't find the page you're after."]
             [:p
              [:a.btn.btn-lg.btn-danger {:href "/"} "I want to complain!"]
              " "
              [:a.btn.btn-lg.btn-success {:href "/"} "Just take me home"]]]]]))
