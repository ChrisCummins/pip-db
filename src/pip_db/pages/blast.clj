(ns pip-db.pages.blast
  (:require [clojure.string :as str]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View
(def page-title "BLAST+ Search")

(def ncbi-blast-url "http://www.ncbi.nlm.nih.gov/BLAST/")

(def bsearch-text
  [:div.row
   [:div.col-md-12
    [:p.lead {:style "margin-bottom:2em;"}
     "Supply a sequence to search using the "
     [:a {:href ncbi-blast-url :target "_blank" :title ncbi-blast-url} "NCBI"]
     " Basic Local Alignment Search Tool."]]])

(defn bsearch-form [request]
  [:form#bs {:method "POST" :action "/s" :enctype "multipart/form-data"}
   (ui/search-form-heading-row "Enter a sequence to perform a search...")
   [:div.row [:div.col-md-6.col-md-offset-2 (ui/textarea-widget "seq" 6 "" "")]]
   (ui/search-form-heading-row "Or select a sequence file to upload...")
   [:div.row [:div.col-md-6.col-md-offset-2
              [:input {:id "f"      :name "f"      :type "file"}]
              [:input {:id "f-name" :name "f-name" :type "text"
                       :style "display:none;"}]]]
   [:hr]
   [:div.row [:div.col-md-2.col-md-offset-6
              (ui/search-form-submit-button "BLAST+ Search")]]])

;; ### Page Layout
(defn view [request]
  (ui/page
   request
   {:title page-title
    :navbar {}
    :heading {:title page-title}
    :body (list
           [:div                    bsearch-text]
           [:div.advsearch.bsearch (bsearch-form request)])}))

;; ## Controller

;; Search page ring handler.
(defn GET [request]
  (view request))
