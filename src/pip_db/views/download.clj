(ns pip-db.views.download
  (:use [pip-db.views.page :only (page)]
        [hiccup.page :only (include-js)])
  (:require [pip-db.util :as util]
            [pip-db.views.ui :as ui]
            [clojure.string :as str]))

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
   [:button#download.btn.btn-block.btn-warning "Download results.csv"]])

(def actions-row
  [:div.row
   [:div.col-md-5.col-md-offset-7
    [:div.input-group file-format-button download-button]]])

(def results-table
  [:table#table
   [:thead][:tbody]])

(def results-row
  [:div#preview.row
   [:div.col-md-12 [:div#preview-frame results-table [:pre#text]]]])

(defn download [request]
  (page
   request
   {:title (get request :name)
    :navbar {}
    :heading {:title "Download results"}
    :body [:div.download
           results-row
           actions-row
           ui/no-results-found-message]
    :javascript (list (include-js "/js/FileSaver.js")
                      (util/inline-data-js "data" (request :results))
                      (util/inline-js "/js/download.inline.js"))}))
