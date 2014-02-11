(ns pip-db.views.record
  (:use [pip-db.views.layout :only (page)])
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

(defn extern-links [& links]
  [:div.panel.panel-primary.panel-extern
   [:div.panel-heading
    [:h3.panel-title "External Links"]]
   [:div.panel-body
    [:ul.panel-extern-list
     links]]])

(defn extern [name url]
  (if (not (str/blank? url))
    [:li [:a.btn.btn-success.btn-block {:href url :target "_blank"} name]]))

(defn record [data]
  (page {:title (get data :name)
         :navbar {:search true}
         :heading {:title (get data :name)
                   :download "/"}
         :body [:div.record
                [:div.row
                 [:div.col-md-8
                  (properties
                   (property "Name" data :name)
                   (property "Alternative name(s)" data :alt_name)
                   (property "Enzyme Commission number" data :ec)
                   (property "Source" data :source)
                   (property "Location" data :organ)
                   (property "pI" data :pi)
                   (property "Molecular Weight" data :mw)
                   (property "Sub unit no" data :sub_no)
                   (property "Sub unit MW" data :sub_mw)
                   (property "Number of Iso Enzymes" data :no_iso)
                   (property "Valid sequences available" data :valid)
                   (property "Experimental method" data :method)
                   (property "Experimental method" data :temp))
                  [:div.meta-holder
                   [:ul.meta-tags
                    [:li#date-added {:data-date (data :created_at)}]]]]
                 [:div.col-md-4
                  (extern-links
                   (extern "Full Text" (data :citations))
                   (extern "Abstract" (data :abstract))
                   (extern "PubMed" (data :pubmed))
                   (extern "Species Taxonomy" (data :species))
                   (extern "Protein Sequence" (data :sequence)))
                  [:div.panel.panel-primary.panel-reference
                   [:div.panel-heading
                    [:h3.panel-title "Reference this page"]]
                   [:div.panel-body
                    [:blockquote#reference]]]]]]
         :javascript (util/inline-js "/js/record.inline.js")}))

(defn no-record []
  (page {:title "Not Found"
         :navbar {:search true}
         :heading {}
         :body [:div.record
                [:p.lead "Record not found."]]}))
