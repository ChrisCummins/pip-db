(ns pip-db.views.advanced
  (:use [pip-db.views.layout :only (page)]))

(defn advanced []
  (page {
         :title "Advanced Search"
         :navbar {}
         :heading {:title "Advanced Search"}
         :body [:div.advsearch
                [:form#as {:method "GET" :action "/s"}
                 [:div.row
                  [:div.col-md-12 [:h4 "Find proteins with..."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q"}
                                  "all of these words:"]]
                  [:div.col-md-6 [:input {:name "q" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info (str "Find proteins with names that contain "
                                   "these keywords")]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q_eq"}
                                  "this exact word or phrase:"]]
                  [:div.col-md-6 [:input {:name "q_eq" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info "Type exact phrases to match in protein names."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q_any"}
                                  "any of these words:"]]
                  [:div.col-md-6 [:input {:name "q_any" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info "Select proteins from a range of keywords."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q_ne"}
                                  "none of these words:"]]
                  [:div.col-md-6 [:input {:name "q_ne" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info "Exclude proteins which contain these keywords."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q_s"} "source:"]]
                  [:div.col-md-6 [:input {:name "q_s" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info "Enter the Latin binomial or common names."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "q_l"} "location:"]]
                  [:div.col-md-6 [:input {:name "q_l" :type "text"
                                          :autocomplete "off"}]]
                  [:div.col-md-4
                   [:div.info "Enter the location or organ."]]]

                 [:div.row
                  [:div.col-md-12 [:h4 "Then narrow results by..."]]]
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
                   [:label {:for "pi-min"} "isoelectric point:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input {:name "pi_l" :type "text"
                              :autocomplete "off"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px;"
                                       "padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input {:name "pi_h" :type "text"
                              :autocomplete "off"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of isoelectric points."]]]
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
                   [:button.btn.btn-success.pull-right
                    {:type "submit" :name "action" :value "advanced"}
                    "Advanced Search"]]]]]
         :javascript [:script {:src "/js/as.js"}]}))
