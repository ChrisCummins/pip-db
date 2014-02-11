;; ## The Advanced Search page
(ns pip-db.views.advanced
  (:use [pip-db.views.page :only (page)])
  (:require [pip-db.util :as util]
            [pip-db.views.ui :as ui]))

;; ## Search form widgets

(defn search-keywords-all-widget [data]
  (ui/search-form-text-row "q" "all of these words"
                           "Find proteins with names that contain these keywords"
                           (data :search-text)))

(defn search-keywords-exact-widget [data]
  (ui/search-form-text-row "q_eq" "this exact word or phrase"
                           "Type exact phrases to match in protein names"))

(defn search-keywords-any-widget [data]
  (ui/search-form-text-row "q_any" "any of these words"
                           "Select proteins from a range of keywords"))

(defn search-keywords-exclude-widget [data]
  (ui/search-form-text-row "q_ne" "none of these words"
                           "Exclude proteins which contain these keywords"))

(defn search-source-widget [data]
  (ui/search-form-text-row "q_s" "source"
                           "Enter the Latin binomial or common names"))

(defn search-location-widget [data]
  (ui/search-form-text-row "q_l" "location"
                           "Enter the location or organ"))

;; ## Page Layout
(defn advanced [data]
  (page {
         :title "Advanced Search"
         :navbar {}
         :heading {:title "Advanced Search"}
         :body [:div.advsearch
                [:form#as {:method "GET" :action "/s"}

                 (ui/search-form-heading-row "Find proteins with...")
                 (search-keywords-all-widget data)
                 (search-keywords-exact-widget data)
                 (search-keywords-any-widget data)
                 (search-keywords-exclude-widget data)
                 (search-source-widget data)
                 (search-location-widget data)

                 (ui/search-form-heading-row "Then narrow results by...")
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
                 (ui/search-form-pi-widget data)
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
                   [:label {:for "m"} "experimental method:"]]
                  [:div.col-md-6
                   [:select {:name "m"}
                    [:option "Any"]
                    [:option "Analytical gel isoelectric focusing"]
                    [:option "Analytical isoelectric focusing"]
                    [:option "Carrier-free isoelectric focusing"]
                    [:option "Column isoelectric focusing"]
                    [:option "Density gradient isoelectric focusing"]
                    [:option "Disc electrophoresis"]
                    [:option "Disc gel electrophoresis"]
                    [:option "Electrofocusing"]
                    [:option "Electrophoresis"]
                    [:option "Electrostatic Focusing"]
                    [:option "Gel electrophoresis"]
                    [:option "Gel isoelectric focusing"]
                    [:option "Isoelectric density gradient electrophoresis"]
                    [:option "Isoelectric focusing in polyacrylamide gel"]
                    [:option "Isoelectric focusing in polyacrylamide gels"]
                    [:option "Isoelectric focusing on acrylamide gel"]
                    [:option "Isoelectric Focusing with a Carrier Ampholyte"]
                    [:option "Isoelectric focusing"]
                    [:option "Isoelectric fractionation"]
                    [:option "Isoelectrofocusing"]
                    [:option "LKB apparatus"]
                    [:option "LKB electrofocusing apparatus"]
                    [:option "Measurement with an antimony microelectrode"]
                    [:option "Microisoelectric focusing on polyacrylamide gel"]
                    [:option "Polyacrylamide disc electrophoresis"]
                    [:option "Polyacrylamide gel electrofucusing"]
                    [:option "Polyacrylamide gel isoelectric focusing"]
                    [:option "Preparative isoelectric focusing"]
                    [:option "SDS disc electrophoresis"]
                    [:option "SDS gel electrophoresis"]
                    [:option "Stationary electrolysis"]
                    [:option "Thin-layer isoelectric focusing"]
                    [:option "Vesterberg and Svensson method"]]]
                  [:div.col-md-4
                   [:div.info (str "Select the method which was used to "
                                   "determine the result")]]]
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
