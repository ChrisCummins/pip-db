;; # User Interface Components
;;
;; Define a set of common user interface components.
(ns pip-db.views.ui
  (:require [pip-db.util :as util]))

;; The Google analytics tracking snippet, as an inline embedded
;; script. Include this on every page to enable analytics tracking.
(defn google-analytics []
  (util/inline-js "/js/google-analytics.inline.js"))

;; ----------
;; ## Widgets
;;
;; A collection of user interface widgets which can be used to
;; construct pages.

;; ## Input widgets
;;
;; These are widgets which are used for user input, such as performing
;; searches.

;; An input label widget. Optionally, the name of an input can be
;; provided to assign the label to.
(defn label-widget
  ([text]     [:label text])
  ([for text] [:label {:for for} text]))

;; An info text widget to be used to annotate input forms.
(defn info-widget [text]
  [:div.info text])

;; A standard text input widget.
(defn text-input-widget [name value]
  [:input {:id name :name name :type "text"
           :autocomplete "off" :value value}])

;; ### Search form widgets
;;
;; A search form is a full-page width entry form for looking up data,
;; and consists of a number of elements aligned into rows.

;; A search form text input widget.
(defn search-form-text-input-widget [name value]
  [:div.col-md-6 (text-input-widget name value)])

(defn search-form-heading-row
  ([text]    [:div.row [:div.col-md-12 [:h4          text]]])
  ([id text] [:div.row [:div.col-md-12 [:h4 {:id id} text]]]))

;; A row within a search form consists of three elements, the label,
;; widget and description.
(defn search-form-widget-row [label widget description]
  [:div.row
   [:div.col-md-2 label] widget [:div.col-md-4 description]])

;; A row within a search form for a text search. We can optionally
;; provide a value to set the text box to.
(defn search-form-text-row
  ([name label-text desc-text]
     (search-form-text-row name label-text desc-text ""))
  ([name label-text desc-text input-value]
     (search-form-widget-row (label-widget name (str label-text ":"))
                             (search-form-text-input-widget name input-value)
                             (info-widget (str desc-text ".")))))

;; ### Main search bar

(def submit-button
  [:button.btn.btn-success.disabled {:name "a" :value "s"} "Search"])

(def advanced-button
  [:button.btn.btn-primary          {:name "a" :value "a"} "Advanced"])

;; This is the main search bar which is embedded into the home page
;; and navbar. It provides the ability to search by name, and to go
;; the advanced search page.
(defn search-bar [data]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    [:input#q.form-control {:name "q" :type "text"
                            :value (data :search-text)
                            :autocomplete "off"}]
    [:div.input-group-btn submit-button advanced-button]]])

;; ### Page heading

(defn heading [data]
  [:div.page-title
   [:div.page-title-inner
    ;; Download link for search results.
    (if (data :download)
      [:div.download [:a.btn.btn-warning {:href (data :download)} "Download"]])
    ;; The page title.
    (if (data :title) [:h3 (data :title)])
    ;; Meta tags for displaying information about search results.
    (if (data :meta)
      [:div.info
       [:ul.meta-tags
        [:li (str "Found " (data :meta-results-count)
                  (if (= (data :meta-results-count) 1)
                    " result..." " results..."))]]])]
   [:hr]])

;; ---------
;; ## Images

;; Returns the path to the logo file of the given dimensions.
(defn logo-path [dimensions] (str "/img/logo-" dimensions ".png"))

;; The largest logo image, used for the homepage.
(def big-logo   [:img {:src (logo-path "640x226")
                       :alt-text "pip-db" :title "pip-db"}])

;; The smaller logo image, used in favicons and the navbar.
(def small-logo [:img {:src (logo-path "32x32")
                       :alt-text "pip-db"
                       :style "width:32px;height:32px"}])
