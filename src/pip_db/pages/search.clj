(ns pip-db.pages.search
  (:require [clojure.string :as str]
            [pip-db.pages.advanced :as advanced]
            [pip-db.db :as db]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View

;; The empty table in which results can be shown
(def results-table
  [:table.table.table-striped.table-hover.table-bordered
   {:style "display:none;"}
   [:thead [:tr
            [:td.name     "Protein"]
            [:td.source   "Source"]
            [:td.location "Location"]
            [:td.pi       "pI"]]] [:tbody]])

(defn view [request]
  (ui/page
   request
   {:title ((request :params) "q")
    :navbar {:search true}
    :heading {:meta true :download true}
    :body [:div.sresults
           results-table
           ui/no-results-found-message]
    :javascript (list (util/inline-data-js "data" (request :results))
                      (util/inline-js "/js/search.inline.js"))}))

;; ## Controller

;; Perform a search from the given request map and wrap the results
;; into a `:results` key.
(defn search-results [request]
  (assoc request :results (db/query (request :params))))

;; Serve a search request.
(defn search-handler [request]
  (view (search-results request)))

;; Serve an advanced search page.
(defn advanced-handler [request] (advanced/GET request))

(defn request-action [request]
  ((request :params) "a"))

;; Return the handler to be used for a specific search action. If the
;; action `a` is used, then we use the advanced handler, else we use
;; the normal search.
(defn response-function [request]
  (if (= "a" (request-action request)) advanced-handler search-handler))

;; Search page ring handler.
(defn GET [request]
  ((response-function request) request))

;; Search page ring handler.
(defn GET-json [request]
  (util/json-response (db/query (request :params))))
