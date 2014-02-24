(ns pip-db.pages.record
  (:require [clojure.string :as str]
            [pip-db.db :as db]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View

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

(defn view [request]
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

;; ## Model

;; Accepts a request map and returns a copy of the map with the :id
;; query parameter removed and replaced with an "id" parameter.
(defn remap-id-param [request]
  (let [params          (request :params)
        url-id          (params  :id)
        remapped-params (dissoc (assoc params "id" url-id) :id)]
    (assoc request :params remapped-params)))

;; ## Controller

(defn GET [request]
  (let [data (db/search ((remap-id-param request) :params))]
    (if (pos? (data :No-Of-Records-Matched))
      (view (assoc request :results data))
      (ui/page-404))))

(defn GET-json [request]
  (util/json-response (db/search ((remap-id-param request) :params))))
