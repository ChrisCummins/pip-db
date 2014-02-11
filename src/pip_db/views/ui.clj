;; ## User Interface Components
;;
;; Define a set of common user interface components
(ns pip-db.views.ui
  (:require [pip-db.util :as util]))

(defn google-analytics []
  (util/inline-js "/js/google-analytics.inline.js"))

(defn search-bar [data]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    [:input#q.form-control {:name "q"
                            :type "text"
                            :value (data :search-text)
                            :autocomplete "off"}]
    [:div.input-group-btn
     ;; The inline Submit and Advanced search page buttons.
     [:button.btn.btn-success.disabled {:name "a" :value "s"} "Search"]
     [:button.btn.btn-primary          {:name "a" :value "a"} "Advanced"]]]])

;; A page heading.
(defn heading [data]
  [:div.page-title
   [:div.page-title-inner
    ;; Download link for search results.
    (if (data :download)
      [:div.download [:a.btn.btn-warning {:href (data :download)} "Download"]])
    ;; The page title.
    (if (data :title) [:h3 (data :title)])
    ;; Meta tags for displaying information about search results.
    (if (data :meta)
      [:div.info
       [:ul.meta-tags
        [:li (str "Found " (data :meta-results-count)
                  (if (= (data :meta-results-count) 1)
                    " result..." " results..."))]]])]
   [:hr]])

;; ## Images

;; Returns the path to the logo file of the given dimensions.
(defn logo-path [dimensions] (str "/img/logo-" dimensions ".png"))

;; The largest logo image, used for the homepage.
(def big-logo   [:img {:src (logo-path "640x226")
                       :alt-text "pip-db" :title "pip-db"}])

;; The smaller logo image, used in favicons and the navbar.
(def small-logo [:img {:src (logo-path "32x32")
                       :alt-text "pip-db"
                       :style "width:32px;height:32px"}])
