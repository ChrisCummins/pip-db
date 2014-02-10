(ns pip-db.views.components
  (:use [pip-db.resources :only (resource)]))

(defn inline-css [path]
  [:style (resource path)])

(defn inline-js [path]
  [:script (resource path)])

(defn search-bar [data]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    [:input#q.form-control {:name "q"
                            :type "text"
                            :value (data :search-text)
                            :autocomplete "off"}]
    [:div.input-group-btn
     [:button#search-submit.btn.btn-success "Search"]
     [:a#search-browse.btn.btn-primary {:href "/advanced"} "Advanced"]]]])
