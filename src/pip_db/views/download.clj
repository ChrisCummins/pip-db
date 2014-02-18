(ns pip-db.views.download
  (:use [pip-db.views.page :only (page)]
        [hiccup.page :only (include-js)])
  (:require [pip-db.util :as util]
            [clojure.string :as str]
            [clojure.data.json :as json]))

(def file-format-label
  [:label {:for "ff"} "File format: "])

(def file-format-selector
  [:select#ff {:name "ff"}
   [:option {:value "csv"}  "CSV"]
   [:option {:value "xml"}  "XML"]
   [:option {:value "json"} "JSON"]])

(def file-format
  (list file-format-label file-format-selector))

(def download-button [:button#download.btn.btn-warning "Download"])

(def actions-row
  [:div.row
   [:div.col-md-12 [:div.pull-right file-format download-button]]])

(def results-table
  [:table#table
   [:thead][:tbody]])

(def results-row
  [:div.row [:div.col-md-12 [:div#preview-frame results-table [:pre#text]]]])

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
