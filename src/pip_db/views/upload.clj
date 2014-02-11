(ns pip-db.views.upload
  (:use [pip-db.views.page :only (page)]))

(defn upload []
  (page {
         :title "Upload"
         :navbar {}
         :heading {:title "Add new data"}
         :body [:div.advsearch
                [:form {:method "POST" :action "/upload"
                        :enctype "multipart/form-data"}
                 [:div.row
                  [:div.col-md-12 [:h4 "1. Upload new data from file..."]]]
                 [:div.row
                  [:div.col-md-12 [:input {:name "f" :type "file"}]]]
                 [:div.row
                  [:div.col-md-12
                   [:div.info "Accepted file formats: .xls, .xlsx, .csv"]]]
                 [:hr]
                 [:div.row
                  [:div.col-md-12 [:h4 "2. Or add a new record..."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "words-all"} "protein name:"]]
                  [:div.col-md-6 [:input#name {:type "text"}]]
                  [:div.col-md-4
                   [:div.info "Enter the exact name of the protein."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "source"} "source:"]]
                  [:div.col-md-6 [:input#source {:type "text"}]]
                  [:div.col-md-4
                   [:div.info "Enter the Latin binomial or common names."]]]
                 [:div.row
                  [:div.col-md-2 [:label {:for "location"} "location:"]]
                  [:div.col-md-6 [:input#location {:type "text"}]]
                  [:div.col-md-4
                   [:div.info "Enter the location or organ."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "location"} "enzyme commission number:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input#ec1 {:type "text"}]]
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input#ec2 {:type "text"}]]
                    [:div {:style "display: table-cell; padding-right: 16px;"}
                     [:input#ec3 {:type "text"}]]
                    [:div {:style "display: table-cell;"}
                     [:input#ec4 {:type "text"}]]]]
                  [:div.col-md-4
                   [:div.info "Enter the E.C."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "pi-min"} "isoelectric point:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input#pi-min {:type "text"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px;"
                                       "padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input#pi-max {:type "text"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of isoelectric points."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "location"} "molecular weight:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input#mw-min {:type "text"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px; padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input#mw-max {:type "text"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of molecular weights."]]]
                 [:div.row
                  [:div.col-md-2
                   [:label {:for "location"} "experimental method:"]]
                  [:div.col-md-6
                   [:select
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
                   [:label {:for "temp-min"} "temperature:"]]
                  [:div.col-md-6
                   [:div {:style "display: table; width: 100%;"}
                    [:div {:style "display: table-cell;"}
                     [:input#temp-min {:type "text"}]]
                    [:div {:style (str "display: table-cell; width:40px; "
                                       "padding-right: 6px;"
                                       "padding-left: 6px;"
                                       "text-align: center;")} "to"]
                    [:div {:style "display: table-cell;"}
                     [:input#temp-max {:type "text"}]]]]
                  [:div.col-md-4
                   [:div.info
                    "Enter an exact or range of temperatures."]]]

                 [:div.row
                  [:div.col-md-2.col-md-offset-6
                   [:button.btn.btn-primary.pull-right
                    {:type "submit" :name "action" :value "upload"}
                    "Submit"]]]]]}))
