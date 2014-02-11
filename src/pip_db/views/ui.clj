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

(defn search-form-heading-row
  ([text]    [:div.row [:div.col-md-12 [:h4          text]]])
  ([id text] [:div.row [:div.col-md-12 [:h4 {:id id} text]]]))

;; A row within a search form consists of three elements, the label,
;; widget and description.
(defn search-form-widget-row [label widget description]
  [:div.row
   [:div.col-md-2 label]
   [:div.col-md-6 widget]
   [:div.col-md-4 description]])

;; A row within a search form for a text search. We can optionally
;; provide a value to set the text box to.
(defn search-form-text-row
  ([name label-text desc-text]
     (search-form-text-row name label-text desc-text ""))
  ([name label-text desc-text input-value]
     (search-form-widget-row (label-widget name (str label-text ":"))
                             (text-input-widget name input-value)
                             (info-widget (str desc-text ".")))))

(defn search-bar [data]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    [:input#q.form-control {:name "q"
                            :type "text"
                            :value (data :search-text)
                            :autocomplete "off"}]
    [:div.input-group-btn
     ;; The inline Submit and Advanced search page buttons.
     [:button.btn.btn-success.disabled {:name "a" :value "s"} "Search"]
     [:button.btn.btn-primary          {:name "a" :value "a"} "Advanced"]]]])

;; A page heading.
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
