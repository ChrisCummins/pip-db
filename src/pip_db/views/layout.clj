(ns pip-db.views.layout
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]
        [pip-db.views.components])
  (:require [pip-db.views.navbar :as navbar]
            [pip-db.views.footer :as footer]))

(defn heading [data]
  [:div.page-title
   [:div.page-title-inner
    (if (data :download)
      [:div.download [:a.btn.btn-warning {:href (data :download)} "Download"]])
    (if (data :title) [:h3 (data :title)])

    (if (data :meta)
      [:div.info
       [:ul.meta-tags
        [:li (str "Found " (data :meta-results-count)
                  (if (= (data :meta-results-count) 1)
                    " result..." " results..."))]]])]
   [:hr]])

(defn google-analytics []
  (inline-js "/js/google-analytics.inline.js"))

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
          (google-analytics)]

         [:body
          (if (data :navbar) (navbar/html (data :navbar)))
          [:div#wrap [:div.container
                      (if (data :heading) (heading (data :heading)))
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
