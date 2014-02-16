;; ## The Advanced Search page
(ns pip-db.views.advanced
  (:use [pip-db.views.page :only (page)])
  (:require [pip-db.util :as util]
            [pip-db.views.ui :as ui]))

;; ## Search form widgets

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

;; ## Page Layout
(defn advanced [request]
  request
  (page {
         :title "Advanced Search"
         :navbar {}
         :heading {:title "Advanced Search"}
         :body [:div.advsearch
                [:form#as {:method "GET" :action "/s"}

                 (ui/search-form-heading-row "Find proteins with...")
                 (search-keywords-all-widget request)
                 (search-keywords-exact-widget request)
                 (search-keywords-any-widget request)
                 (search-keywords-exclude-widget request)
                 (search-source-widget request)
                 (search-location-widget request)

                 (ui/search-form-heading-row "Then narrow results by...")
                 (ui/search-form-method-row request)
                 (ui/search-form-pi-row request)
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "ec1"} "enzyme commission number:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input {:name "ec1" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input {:name "ec2" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input {:name "ec3" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style "display: table-cell;"}
                     [:input {:name "ec4" :type "text"
                              :autocomplete "off"}]]]]
                  [:div.col-md-4
                   [:div.info "Enter one or more categories for the EC."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "location"} "molecular weight:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input {:name "mw_l" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px; padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input {:name "mw_h" :type "text"
                              :autocomplete "off"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of molecular weights."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "t_l"} "temperature:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input {:name "t_l" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px;"
                                       "padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input {:name "t_h" :type "text"
                              :autocomplete "off"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of temperatures."]]]

                 [:div.row
                  [:div.col-md-2.col-md-offset-6
                   [:button.btn.btn-success.disabled.pull-right
                    {:type "submit" :name "a" :value "s"}
                    "Advanced Search"]]]]]}))
