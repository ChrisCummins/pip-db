(ns pip-db.views.record
  (:require [clojure.string :as str]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

(def properties-table
  [:table#properties.table.table-striped.table-bordered
   {:style "display:none;"} [:tbody]])

;; External links are presented inside of a panel.
(def extern-links-panel
  [:div.panel.panel-primary.panel-extern {:style "display:none;"}
   [:div.panel-heading [:h3.panel-title "External Links"]]
   [:div.panel-body [:ul]]])

;; Record notes are presented inside of a panel. Note that we don't
;; return anything if the `text` parameter is empty.
(def notes-panel
  [:div.panel.panel-primary.panel-notes {:style "display:none;"}
   [:div.panel-heading [:h3.panel-title "Notes"]]
   [:div.panel-body]])

(def reference-style-dropdown
  [:ul.reference-dropdown.dropdown-menu])

(def reference-style-button
  [:button.reference-style.btn.btn-success.dropdown-toggle.pull-right
   {:data-toggle "dropdown"} "Citation Style " [:span.caret]])

(def reference-heading
  [:h3.panel-title.pull-left "Reference this page"])

(def reference-style
  [:div.btn-group.pull-right reference-style-button reference-style-dropdown])

(def reference-this-page-panel
  [:div.panel.panel-primary.panel-reference {:style "display:none;"}
   [:div.panel-heading reference-heading reference-style [:div.clearfix]]
   [:div.panel-body [:blockquote.reference-text]]])

(defn record [request]
  (let [results (request :results)]
    (ui/page
     request
     {:navbar {:search true}
      :heading {:title " " ; Blank title
                :download (str "/d?id=" ((request :params) :id))}
      :body [:div.record
             [:div.row
              [:div.col-md-8
               properties-table
               [:div.meta-holder [:ul.meta-tags [:li#date-added]]]]
              [:div.col-md-4
               extern-links-panel
               notes-panel
               reference-this-page-panel]]]
      :javascript (list (util/inline-data-js "data" results)
                        (util/inline-js "/js/record.inline.js"))})))
