(ns pip-db.views.search
  (:use [pip-db.views.layout :only (page)]
        [pip-db.views.components :only (inline-js)]))

(defn tablify-results [results]
  [:table.table.table-striped.table-hover.table-bordered
   [:thead
    [:tr
     [:td [:strong "Protein"]]
     [:td [:strong "Source"]]
     [:td [:strong "Location"]]
     [:td [:strong "pI"]]]]
   [:tbody
    (for [record results]
      [:tr {:data-id (record :id)}
       [:td (record :name)]
       [:td (record :source)]
       [:td (record :organ)]
       [:td (record :pi)]])]])

(defn search [query data]
  (page {:title query
         :navbar {:search true :search-text query}
         :heading {:meta true
                   :meta-results-count (data :results-count)
                   :download "/"}
         :body [:div.sresults
                (if (> (data :results-count) 0)
                  (tablify-results (data :results))
                  [:p.lead "No results found."])]
         :javascript (inline-js "/js/search.js")}))
