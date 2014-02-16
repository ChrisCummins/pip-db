(ns pip-db.views.record
  (:use [pip-db.views.page :only (page)])
  (:require [pip-db.util :as util]
            [clojure.string :as str]))

(defn properties [& properties]
  [:table#properties.table.table-striped.table-bordered
   [:tbody properties]])

(defn property [description data key]
  (let [value (data key)]
    (if (not (str/blank? value))
      [:tr.property {:data-key (name key)}
       [:td.description description][:td.value value]])))

;; Represent a numerical property range by providing data keys for
;; both the min and max values. In this case, the resulting row is
;; given two additional attributes `min-key` and `max-key`.
(defn property-range [description data min-key max-key]
  (let [min-value (data min-key)
        max-value (data max-key)]
    (if (and (not (str/blank? min-value))
             (not (str/blank? max-value)))
      [:tr.property {:data-key-min (name min-key)
                     :data-key-max (name max-key)}
       [:td.description description]
       [:td.value (str min-value " - " max-value)]])))

;; External links are presented inside of a panel.
(defn extern-links [& links]
  (if (not (empty? links))
    [:div.panel.panel-primary.panel-extern
     [:div.panel-heading
      [:h3.panel-title "External Links"]]
     [:div.panel-body
      [:ul.panel-extern-list
       links]]]))

(defn extern [name url]
  (if (not (str/blank? url))
    [:li [:a.btn.btn-success.btn-block {:href url :target "_blank"} name]]))

;; Record notes are presented inside of a panel. Note that we don't
;; return anything if the `text` parameter is empty.
(defn notes-panel [text]
  (if (not (str/blank? text))
    [:div.panel.panel-primary
     [:div.panel-heading [:h3.panel-title "Notes"]]
     [:div.panel-body text]]))

(defn record [request]
  (page
   request
   {:title (get request :name)
    :navbar {:search true}
    :heading {:title (get request :name)
              :download "/"}
    :body [:div.record
           [:div.row
            [:div.col-md-8
             (properties
              (property "Name" request :name)
              (property "Alternative name(s)" request :alt_name)
              (property "Enzyme Commission number" request :ec)
              (property "Source" request :source)
              (property "Location" request :organ)
              (property "pI" request :pi)
              (property-range "pI" request :pi_range_min :pi_range_max)
              (property "pI Maximum" request :pi_max)
              (property "pI of major component" request :pi_major)
              (property "Molecular Weight" request :mw)
              (property "Sub unit no" request :sub_no)
              (property "Sub unit MW" request :sub_mw)
              (property "Number of Iso Enzymes" request :no_iso)
              (property "Valid sequences available" request :valid)
              (property "Experimental method" request :method)
              (property "Experimental method" request :temp))
             [:div.meta-holder
              [:ul.meta-tags
               [:li#date-added {:data-date (request :created_at)}]]]]
            [:div.col-md-4
             (extern-links
              (extern "Full Text" (request :citations))
              (extern "Abstract" (request :abstract))
              (extern "PubMed" (request :pubmed))
              (extern "Species Taxonomy" (request :species))
              (extern "Protein Sequence" (request :sequence)))
             (notes-panel (request :notes))
             [:div.panel.panel-primary.panel-reference
              [:div.panel-heading
               [:h3.panel-title "Reference this page"]]
              [:div.panel-body
               [:blockquote#reference]]]]]]
    :javascript (util/inline-js "/js/record.inline.js")}))
