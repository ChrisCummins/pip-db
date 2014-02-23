(ns pip-db.views.page
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)])
  (:require [pip-db.util :as util]
            [pip-db.views.ui :as ui]
            [pip-db.views.navbar :as navbar]))

(def empty-request {:params {}})

(defn render-request [request]
  (html5 {:lang "en" :class "no-js"}
         [:head
          [:meta {:charset "utf-8"}]
          [:meta {:http-equiv "X-UA-Compatible"
                  :content "IE=edge,chrome=1"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          [:meta {:name "msapplication-tooltip"
                  :content "Protein Isoelectric Point Database."}]
          [:title (str "pip-db " (request :title))]
          ui/favicon

          (include-css "/css/styles.css"
                       "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css")
          (request :header)
          (include-js "/js/modernizr-2.7.0.min.js")
          (ui/google-analytics)]

         [:body
          (if (request :navbar) (navbar/html request))
          [:div#wrap [:div.container
                      (if (request :heading) (ui/heading (request :heading)))
                      (request :body)]]
          (if (not (request :hide-footer)) (ui/footer))

          (include-js
           "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
          [:script "window.jQuery || document.write('"
           "<script src=\"/js/jquery-1.10.2.min.js\"><\\/script>');"]
          (include-js "/js/bootstrap-3.0.1.min.js"
                      "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"
                      "/js/main.js"
                      "/js/moment.min.js")
          (request :javascript)]))

(defn page
  ([contents]         (page empty-request contents))
  ([request contents] (render-request (merge request contents))))
