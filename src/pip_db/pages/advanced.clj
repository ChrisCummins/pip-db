(ns pip-db.pages.advanced
  (:require [pip-db.util :as util]
            [pip-db.ui :as ui]))

;; ## View

(defn search-keywords-all-widget [request]
  (ui/search-form-text-row "q" "all of these words"
                           "Find proteins with names that contain these keywords"
                           ((request :params) "q")))

(defn search-keywords-exact-widget [request]
  (ui/search-form-text-row "q_eq" "this exact word or phrase"
                           "Type exact phrases to match in protein names"
                           ((request :params) "q_eq")))

(defn search-keywords-any-widget [request]
  (ui/search-form-text-row "q_any" "any of these words"
                           "Select proteins from a range of keywords"
                           ((request :params) "q_any")))

(defn search-keywords-exclude-widget [request]
  (ui/search-form-text-row "q_ne" "none of these words"
                           "Exclude proteins which contain these keywords"
                           ((request :params) "q_ne")))

(defn search-source-widget [request]
  (ui/search-form-text-row "q_s" "source"
                           "Enter the Latin binomial or common names"
                           ((request :params) "q_s")))

(defn search-location-widget [request]
  (ui/search-form-text-row "q_l" "location"
                           "Enter the location or organ"
                           ((request :params) "q_l")))

(defn search-method-widget [request]
  (ui/search-form-text-row "m" "experimental method"
                           "Enter the experimental method used to determine the result."
                           ((request :params) "m")))

(defn ec-input-cell
  ([name] (ec-input-cell name ""))
  ([name value]
     [:div.ec
      [:input {:name name :value value :type "text" :autocomplete "off"}]]))

(defn search-ec-widget [request]
  (ui/search-form-widget-row
   (ui/label-widget "enzyme commission number:")
   [:div.col-md-6
    [:div {:style "display:table;width:100%;"}
     (ec-input-cell "ec1" ((request :params) "ec1"))
     (ec-input-cell "ec2" ((request :params) "ec2"))
     (ec-input-cell "ec3" ((request :params) "ec3"))
     (ec-input-cell "ec4" ((request :params) "ec4"))]]
   (ui/info-widget "Enter one or more categories for the EC.")))

(defn numerical-range-widget [label desc in-l val-l in-h val-h]
  (ui/search-form-widget-row
   (ui/label-widget (str label ":"))
   [:div.col-md-6
    [:div {:style "display:table;width:100%;"}
     [:div {:style "display:table-cell;"}
      [:input {:name in-l :value val-l :type "text" :autocomplete "off"}]]
     [:div {:style (str "display:table-cell;width:40px;padding-right:6px;"
                        "padding-left:6px;text-align: center;")} "to"]
     [:div {:style "display:table-cell;"}
      [:input {:name in-h :value val-h :type "text" :autocomplete "off"}]]]]
   (ui/info-widget desc)))

(defn search-form-mw-widget [request]
  (numerical-range-widget
   "molecular weight" "Enter an exact or range of molecular weights."
   "mw_l" ((request :params) "mw_l") "mw_h" ((request :params) "mw_l")))

(defn search-form-temp-widget [request]
  (numerical-range-widget
   "temperature" "Enter an exact or range of temperatures."
   "t_l" ((request :params) "t_l") "t_h" ((request :params) "t_h")))

(defn search-form-heading-row
  ([text]    [:div.row [:div.col-md-12 [:h4          text]]])
  ([id text] [:div.row [:div.col-md-12 [:h4 {:id id} text]]]))

(defn primary-search-fields [request]
  (list
   (search-form-heading-row "Find proteins with...")
   (search-keywords-all-widget request)
   (search-keywords-exact-widget request)
   (search-keywords-any-widget request)
   (search-keywords-exclude-widget request)
   (search-source-widget request)
   (search-location-widget request)))

(defn secondary-search-fields [request]
  (list
   (search-form-heading-row "Then narrow results by...")
   (ui/search-form-pi-row request)
   (search-method-widget request)
   (search-ec-widget request)
   (search-form-mw-widget request)
   (search-form-temp-widget request)))

(def submit-row
  [:div.row
   [:div.col-md-offset-2.col-md-4
    {:style "padding-right:0;"
     :title "The number of matching records"}
    [:div#results-count
     [:div.ui-progressbar-label]]]
   [:div.col-md-2
    [:button.btn.btn-block.btn-success.disabled
     {:type "submit" :name "a" :value "s"}
     "Advanced Search"]]])

(defn advs-form [request]
  [:form#as {:method "GET" :action "/s"}
   (primary-search-fields request)
   (secondary-search-fields request)
   submit-row])

;; ## Page Layout
(defn view [request]
  (ui/page
   request
   {:title "Advanced Search"
    :navbar {}
    :heading {:title "Advanced Search"}
    :body [:div.advsearch (advs-form request)]}))

;; ## Controller

;; Serve an advanced search page.
(defn GET [request] (view request))
