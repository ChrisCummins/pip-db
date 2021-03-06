(ns pip-db.pages.download
  (:use [hiccup.page :only (include-js)])
  (:require [clojure.string :as str]
            [pip-db.search :as search]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View

(def file-format-button
  [:div.input-group-btn.dropup
   [:button.btn.btn-block.btn-success.dropdown-toggle
    {:data-toggle "dropdown"} "Select File Format"]
   [:ul#ff.dropdown-menu]])

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

(defn view [request]
  (ui/page
   request
   {:title (get request :name)
    :navbar {}
    :heading {:title "Download results"}
    :body (list [:div.download {:style "display:none;"}
                 results-row
                 actions-row]
                ui/no-results-found-message)
    :javascript (list (include-js "/js/FileSaver.js")
                      (util/inline-data-js "data" (request :results))
                      (util/inline-js "/js/download.inline.js"))}))

;; ## Controller

;; Perform a search from the given request map and wrap the results
;; into a `:results` key.
(defn search-results [request]
  (assoc request :results (search/search request)))

(defn GET [request] (view request)
  (view (search-results request)))
