(ns pip-db.views.search
  (:use [pip-db.views.page :only (page)])
  (:require [clojure.string :as str]
            [pip-db.util :as util]
            [pip-db.ui :as ui]))

;; The empty table in which results can be shown
(def results-table
  [:table.table.table-striped.table-hover.table-bordered
   {:style "display:none;"}
   [:thead [:tr
            [:td.name     "Protein"]
            [:td.source   "Source"]
            [:td.location "Location"]
            [:td.pi       "pI"]]] [:tbody]])

(defn search [request]
  (page request
        {:title ((request :params) "q")
         :navbar {:search true}
         :heading {:meta true :download true}
         :body [:div.sresults
                results-table
                ui/no-results-found-message]
         :javascript (list (util/inline-data-js "data" (request :results))
                           (util/inline-js "/js/search.inline.js"))}))
