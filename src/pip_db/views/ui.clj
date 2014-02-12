;; # User Interface Components
;;
;; Define a set of common user interface components.
(ns pip-db.views.ui
  (:require [pip-db.util :as util]
            [pip-db.resources :as res]))

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

;; A hidden text input widget.
(defn hidden-input-widget
  ([name]
     [:input {:id name :name name :type "text"
              :style "display:none;"}])
  ([name value]
     [:input {:id name :name name :type "text" :value value
              :style "display:none;"}]))

;; An On/Off button, in the "Off" position.
(defn off-on-button-input-widget [id]
  [:button.btn.btn-block.btn-primary {:id id :type "button"} "Off"])

;; An On/Off button, in the "On" position.
(defn on-off-button-input-widget [id]
  [:button.btn.btn-block.btn-warning {:id id :type "button"} "On"])

;; A jQuery UI slider range widget. This consists of three elements:
;; the div to contain the slider itself, and two invisible form inputs
;; which are used to store the slider values.
(defn range-slider-input-widget [id name-low name-high]
  (list [:div {:id id}]
        (hidden-input-widget name-low)
        (hidden-input-widget name-high)))

;; ### Search form widgets
;;
;; A search form is a full-page width entry form for looking up data,
;; and consists of a number of elements aligned into rows.

;; A search form text input widget.
(defn search-form-text-input-widget [name value]
  [:div.col-md-6 (text-input-widget name value)])

;; The search form pI input slider.
(defn search-form-pi-input-widget [data]
  (list [:div.col-md-1
         (off-on-button-input-widget "pi-active")]
        [:div.col-md-5 {:style "padding-left:0;"}
         (range-slider-input-widget "pi-slider" "pi_l" "pi_h")]))

;; The search form experiment method input selector.
(defn search-form-method-input-widget [data]
  (list [:div.col-md-1
         (off-on-button-input-widget "m-active")]
        [:div.col-md-5 {:style "padding-left:0;"}
         [:select {:id "m-select" :disabled true}
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
        (hidden-input-widget "m")))

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

;; An isoelectric point input search form widget.
(defn search-form-pi-row [data]
  (search-form-widget-row (label-widget "isoelectric point (pH):")
                          (search-form-pi-input-widget data)
                          (info-widget (str "Enter an exact or range of "
                                            "isoelectric points."))))

;; An experimental method selection search form row.
(defn search-form-method-row [data]
  (search-form-widget-row (label-widget "m" "experimental method:")
                          (search-form-method-input-widget data)
                          (info-widget (str "Select the method which was used "
                                            "to determine the result."))))

;; ### Main search bar
;;
;; The search bar consists of three elements:

;; ### 1. The input text field
;;
;; This can be loaded with a value already typed in.
(defn search-bar-input [text]
  [:input#q.form-control {:name "q" :type "text" :value text
                          :autocomplete "off"}])

;; ### 2. Submit button
;;
;; This completes the search and takes the user to the results page.
(def submit-button
  [:button.btn.btn-success.disabled {:name "a" :value "s"} "Search"])

;; ### 3. Advanced Search button
;;
;; This takes the user to the advanced search page.
(def advanced-button
  [:button.btn.btn-primary          {:name "a" :value "a"} "Advanced"])

;; This is the main search bar which is embedded into the home page
;; and navbar. It provides the ability to search by name, and to go
;; the advanced search page.
(defn search-bar [data]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    (search-bar-input (data :search-text))
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
(defn logo-path [dimensions]
  (res/image-path (str "logo-" dimensions ".png")))

;; The largest logo image, used for the homepage.
(def big-logo   [:img {:src (logo-path "640x226")
                       :alt-text "pip-db" :title "pip-db"}])

;; The smaller logo image, used in favicons and the navbar.
(def small-logo [:img {:src (logo-path "32x32")
                       :alt-text "pip-db"
                       :style "width:32px;height:32px"}])
