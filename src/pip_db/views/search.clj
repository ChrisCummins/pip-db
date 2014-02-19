(ns pip-db.views.search
  (:use [pip-db.views.page :only (page)])
  (:require [clojure.string :as str]
            [pip-db.util :as util]
            [pip-db.views.ui :as ui]))

;; The empty table in which results can be shown
(def results-table
  [:table.table.table-striped.table-hover.table-bordered
   [:thead [:tr
            [:td.name     "Protein"]
            [:td.source   "Source"]
            [:td.location "Location"]
            [:td.pi       "pI"]]] [:tbody]])

(defn page-links [current-page pages pages-count]
  (list
   (if (> (first pages) 1)
     [:a.page-ref.btn.btn-success {:data-page 1} "&laquo;"])
   (for [page pages]
     [:a.page-ref.btn.btn-success
      (if (= page current-page)
        {:data-page page :class "disabled"}
        {:data-page page}) page])
   (if (< (last pages) pages-count)
     [:a.page-ref.btn.btn-success {:data-page pages-count} "&raquo;"])))

;; Generate the row of pagination links, if required. We determine if
;; we need pagination links based on whether we have more than one
;; page or not.
(defn pagination-links [current-page pages results-per-page pages-count]
  (if (> pages-count 1)
    [:div.row {:style "margin-bottom: 20px;"}
     [:div.col-lg-12
      [:div {:style "text-align: center;"}
       [:div#pagination.btn-group {:style "margin: 0 auto;"
                                   :data-results-per-page results-per-page
                                   :data-pages-count pages-count}
        (page-links current-page pages pages-count)]]]]))

(def beta-warning
  [:div#limited-results.alert.alert-info {:style "display:none;"}
   [:strong "Limited Results "]
   "The number of results has been limited for the beta version of the website."
   [:a.close {:href "#" :data-dismiss "alert"
              :aria-hidden "true"} "&times;"]])

(defn search [request]
  (page request
        {:title ((request :params) "q")
         :navbar {:search true}
         :heading {:meta true
                   :meta-results-count (request :results-count)
                   :download true}
         :body [:div.sresults
                beta-warning
                results-table
                ui/no-results-found-message]
         :javascript (list (util/inline-data-js "data" (request :results))
                           (util/inline-js "/js/search.inline.js"))}))
