(ns pip-db.views.page
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [pip-db.util :as util]
            [pip-db.views.ui :as ui]
            [pip-db.views.navbar :as navbar]
            [pip-db.views.footer :as footer]))

(defn page [data]
  (html5 {:lang "en" :class "no-js"}
         [:head
          [:meta {:charset "utf-8"}]
          [:meta {:http-equiv "X-UA-Compatible"
                  :content "IE=edge,chrome=1"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          [:meta {:name "msapplication-tooltip"
                  :content "Protein Isoelectric Point Database."}]
          [:title (str "pip-db " (data :title))]
          (include-css "/css/styles.css")
          (data :header)
          (include-js "/js/modernizr-2.7.0.min.js")
          (ui/google-analytics)]

         [:body
          (if (data :navbar) (navbar/html (data :navbar)))
          [:div#wrap [:div.container
                      (if (data :heading) (ui/heading (data :heading)))
                      (data :body)]]
          (if (not (data :hide-footer)) (footer/html))

          (include-js
           "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
          [:script "window.jQuery || document.write('"
           "<script src=\"/js/jquery-1.10.2.min.js\"><\\/script>');"]
          (include-js "/js/bootstrap-3.0.1.min.js"
                      "/js/main.js"
                      "/js/moment.min.js")
          (data :javascript)]))

(defn not-found []
  (page {:title "Page Not Found",
         :navbar {:search false}
         :body [:div.row
                [:div.col-lg-12.text-center
                 [:div.jumbotron.errortron
                  [:h1 "404 :("]
                  [:p "Sorry, I couldn't find the page you're after."]
                  [:p
                   [:a.btn.btn-lg.btn-danger {:href "/"}
                    "I want to complain!"] " "
                   [:a.btn.btn-lg.btn-success {:href "/"}
                    "Just take me home"]]]]]}))
