(ns pip-db.views.download
  (:use [pip-db.views.page :only (page)]
        [hiccup.page :only (include-js)])
  (:require [pip-db.util :as util]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(def file-format-dropdown
  [:ul#ff.dropdown-menu
   [:li [:a {:href "#preview" :data-format "xml"}  "XML"]]
   [:li.disabled [:a {:href "#preview" :data-format "csv"}  "CSV"]]
   [:li [:a {:href "#preview" :data-format "json"} "JSON"]]])

(def file-format-button
  [:div.input-group-btn.dropup
   [:button.btn.btn-block.btn-success.dropdown-toggle {:data-toggle "dropdown"}
    "Select File Format"]
   file-format-dropdown])

(def download-button
  [:div.input-group-btn
   [:button#download.btn.btn-block.btn-warning "Download"]])

(def actions-row
  [:div.row
   [:div.col-md-4.col-md-offset-8
    [:div.input-group file-format-button download-button]]])

(def results-table
  [:table#table
   [:thead][:tbody]])

(def results-row
  [:div#preview.row
   [:div.col-md-12 [:div#preview-frame results-table [:pre#text]]]])

;; It's necessary to extend the SQL Timetsamp type in order to
;; JSON-ify it. See: http://stackoverflow.com/a/19164491
(extend-type java.sql.Timestamp
  json/JSONWriter
  (-write [date out]
    (json/-write (str date) out)))

(defn results-json [results]
  (str "var data = " (json/write-str results)))

(defn download [request]
  (page
   request
   {:title (get request :name)
    :navbar {}
    :heading {:title "Download results"}
    :body [:div.download
           (if (> (request :results-count) 0)
             (list results-row actions-row)
             [:p.lead "No results found."])]
    :javascript (list (include-js "/js/FileSaver.js")
                      [:script (results-json (request :results))]
                      (util/inline-js "/js/download.inline.js"))}))
