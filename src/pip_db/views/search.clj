(ns pip-db.views.search
  (:use [pip-db.views.page :only (page)])
  (:require [clojure.string :as str]
            [pip-db.util :as util]))

;; If a range of pI values are to be displayed, then we use a
;; separator string to delimit the minimum and maximum values.
(def pi-range-separator "-")

;; We allow for some flexibility in displaying isoelectric points. We
;; will try first to show an exact value, else a range of values, or
;; just an individual result within that range.
(defn pi-text [record]
  (let [pi       (record :pi)
        pi-min   (record :pi_range_min)
        pi-max   (record :pi_range_max)
        pi-major (record :pi_major)]

    (if (not (str/blank? pi))
      pi
      (if (not (str/blank? pi-major))
        (str pi-major "m")
        (if (and (not (str/blank? pi-min))
                 (not (str/blank? pi-max)))
          (str pi-min pi-range-separator pi-max)
          (str pi-min pi-max))))))

(defn tablify-results [results]
  [:table.table.table-striped.table-hover.table-bordered
   [:thead
    [:tr
     [:td.name     "Protein"]
     [:td.source   "Source"]
     [:td.location "Location"]
     [:td.pi       "pI"]]]
   [:tbody
    (for [record results]
      [:tr {:data-id (record :id)}
       [:td.name     (record :name)]
       [:td.source   (record :source)]
       [:td.location (record :organ)]
       [:td.pi       (pi-text record)]])]])

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

(defn beta-warning []
  [:div.alert.alert-info
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
                   :download "/"}
         :body [:div.sresults
                (if (> (request :results-count) 0)
                  (list
                   (if (request :limited-results)
                     (beta-warning))
                   (tablify-results (request :results))
                   (pagination-links (request :current-page) (request :pages)
                                     (request :results-per-page)
                                     (request :pages-count)))
                  [:p.lead "No results found."])]
         :javascript (util/inline-js "/js/search.inline.js")}))
