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

(defn pagination-links [current-page pages results-per-page pages-count]
  [:div.row {:style "margin-bottom: 20px;"}
   [:div.col-lg-12
    [:div {:style "text-align: center;"}
     [:div#pagination.btn-group {:style "margin: 0 auto;"
                                 :data-results-per-page results-per-page
                                 :data-pages-count pages-count}
      (page-links current-page pages pages-count)]]]])


(defn search [query data]
  (page {:title query
         :navbar {:search true :search-text query}
         :heading {:meta true
                   :meta-results-count (data :results-count)
                   :download "/"}
         :body [:div.sresults
                (if (> (data :results-count) 0)
                  (list
                   (tablify-results (data :results))
                   (pagination-links (data :current-page) (data :pages)
                                     (data :results-per-page)
                                     (data :pages-count)))
                  [:p.lead "No results found."])]
         :javascript (inline-js "/js/search.js")}))
