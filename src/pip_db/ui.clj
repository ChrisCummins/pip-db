;; # User Interface Components
;;
;; Define a set of common user interface components.
(ns pip-db.ui
  (:use [hiccup.page :only (html5 include-css include-js)])
  (:require [clojure.string :as str]
            [pip-db.util :as util]))

;; The Google analytics tracking snippet, as an inline embedded
;; script. Include this on every page to enable analytics tracking.
(defn google-analytics []
  (util/inline-js "/js/google-analytics.inline.js"))

;; The hidden "No results found" message for search results pages.
(def no-results-found-message
  [:p#no-results.lead {:style "display:none;"} "No results found."])

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
(defn text-input-widget [name placeholder value]
  [:input {:id name :name name :type "text" :autocomplete "off"
           :placeholder placeholder :value value}])

;; A multi-line textarea input
(defn textarea-widget [name no-rows value]
  [:textarea {:id name :name name :rows no-rows} value])

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

(defn search-form-heading-row
  ([text]    [:div.row [:div.col-md-12 [:h4          text]]])
  ([id text] [:div.row [:div.col-md-12 [:h4 {:id id} text]]]))

;; A search form text input widget.
(defn search-form-text-input-widget [name placeholder value]
  [:div.col-md-6 (text-input-widget name placeholder value)])

;; The search form pI input slider.
(defn search-form-pi-input-widget [data]
  (list [:div.col-md-1
         (off-on-button-input-widget "pi-active")]
        [:div.col-md-5 {:style "padding-left:0;"}
         (range-slider-input-widget "pi-slider" "pi_l" "pi_h")]))

;; The search form FASTA input widget.
(defn search-form-fasta-input-widget [data]
  [:div.col-md-6 (textarea-widget "seq" 1 data)])

;; A row within a search form consists of three elements, the label,
;; widget and description.
(defn search-form-widget-row [label widget description]
  [:div.row
   [:div.col-md-2 label] widget [:div.col-md-4 description]])

;; A row within a search form for a text search. We can optionally
;; provide a value to set the text box to.
(defn search-form-text-row
  ([name label-text desc-text]
     (search-form-text-row name label-text desc-text "" ""))
  ([name label-text desc-text examples]
     (search-form-text-row name label-text desc-text examples ""))
  ([name label-text desc-text examples input-value]
     (search-form-widget-row (label-widget name (str label-text ":"))
                             (search-form-text-input-widget
                              name (util/placeholder examples) input-value)
                             (info-widget (str desc-text ".")))))

;; An isoelectric point input search form widget.
(defn search-form-pi-row [data]
  (search-form-widget-row (label-widget "isoelectric point (pH):")
                          (search-form-pi-input-widget data)
                          (info-widget (str "Select from a range of "
                                            "isoelectric points."))))

;; A FASTA sequence search form widget.
(defn search-form-fasta-row [request]
  (search-form-widget-row (label-widget "seq" "FASTA sequence:")
                          (search-form-fasta-input-widget ((request :params) "seq"))
                          (info-widget "Enter a FASTA sequence for BLAST+ searches.")))

(defn search-form-submit-button [label]
  [:button.btn.btn-block.btn-success.disabled
   {:type "submit" :name "a" :value "s"} label])

(defn search-form-submit-row [submit-label]
  [:div.row
   [:div.col-md-offset-2.col-md-4
    {:style "padding-right:0;"
     :title "The number of matching records"}
    [:div#results-count
     [:div.ui-progressbar-label]]]
   [:div.col-md-2 (search-form-submit-button submit-label)]])

;; ### Main search bar
;;
;; The search bar consists of four elements:

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

;; ### 4. BLAST+ button
;;
;; This takes the user to the BLAST+ search page.
(def blast-button
  [:button.btn.btn-warning          {:name "a" :value "b"} "BLAST"])

;; This is the main search bar which is embedded into the home page
;; and navbar. It provides the ability to search by name, and to go
;; the advanced search page.
(defn search-bar [request]
  [:form {:method "GET" :action "/s" :role "search"}
   [:div.input-group
    (search-bar-input ((request :params) "q"))
    [:div.input-group-btn submit-button advanced-button blast-button]]])

;; ### Page heading

(defn heading [data]
  (let [download (data :download)]
    [:div.page-title
     [:div.page-title-inner
      ;; Download link for search results.
      (if download
        [:div.download [:a#download.btn.btn-warning
                        {:style "display:none;"
                         :href (if (string? download) download "#")}
                        "Download"]])
      ;; The page title.
      (if (data :title) [:h3 (data :title)])
      ;; Meta tags for displaying information about search results.
      (if (data :meta)
        [:div.info [:ul.meta-tags [:li.results-count]]])]
     [:hr]]))

;; ### Page footer

(defn footer []
  [:div.footer
   [:div.container.text-center
    [:hr]
    [:ul.footer-nav
     [:li "© " [:a.muted {:href "http://chriscummins.cc"
                          :target "_blank" :title "Link opens in new window"}
                "Chris Cummins"] " " (util/current-year)]]]])

;; ---------
;; ## Images

;; Returns the path to the logo file of the given dimensions.
(defn logo-path [dimensions]
  (util/image-path (str "logo-" dimensions ".png")))

;; The largest logo image, used for the homepage.
(def big-logo   [:img {:src (logo-path "640x226")
                       :alt-text "pip-db" :title "pip-db"}])

;; The smaller logo image, used in favicons and the navbar.
(def small-logo [:img {:src (logo-path "32x32")
                       :alt-text "pip-db"
                       :style "width:32px;height:32px"}])

(def favicon [:link {:rel "icon" :type "image/png" :href "/favicon.png"}])

;; ---------
;; ## Navbar

(defn navbar-search [request]
  [:div.search.navbar-search (search-bar request)])

;; Generate the user menu for the navbar.
(defn navbar-user [request]
  [:ul.nav.navbar-nav.navbar-right
   (if (util/signed-in? request)
     [:li.dropdown
      [:a#themes.dropdown-toggle {:href "#" :data-toggle "dropdown"}
       (util/username request) [:span.caret]]
      [:ul.dropdown-menu
       [:li [:a {:href "/" :tabindex "-1"} "Run initial setup"]]
       [:li [:a {:href "/upload" :tabindex "-1"} "Upload new data"]]
       [:li [:a {:href "/" :tabindex "-1"} "Preferences"]]
       [:li.divider]
       [:li [:a {:href "/logout" :tabindex "-1"} "Log out"]]]]
     [:li [:a {:href "/login"} "Login"]])])

(defn navbar [request]
  (let [navbar (request :navbar)]
    [:div.navbar.navbar-fixed-top

     {:class (if (navbar :login-only)
               "navbar-invisible"
               "navbar-default")}

     [:div.container
      [:div.navbar-header
       [:button.navbar-toggle {:type "button"
                               :data-toggle "collapse"
                               :data-target "navbar-collapse"}
        [:span.icon-bar]
        [:span.icon-bar]
        [:span.icon-bar]]
       [:a.navbar-brand {:href "/"} small-logo "pip-db"]]

      [:div.navbar-collapse.collapse
       (if (navbar :search) (navbar-search request))
       (if (not (navbar :hide-user)) (navbar-user request))]]]))

;; ------------------
;; ## Page Templating

(def meta-tags
  (list [:meta {:charset "utf-8"}]
        [:meta {:http-equiv "X-UA-Compatible"
                :content "IE=edge,chrome=1"}]
        [:meta {:name "viewport"
                :content "width=device-width, initial-scale=1"}]
        [:meta {:name "msapplication-tooltip"
                :content "Protein Isoelectric Point Database."}]))

(defn page-head [request]
  [:head
   meta-tags
   [:title (str "pip-db " (request :title))]
   favicon

   (include-css "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/smoothness/jquery-ui.css"
                "/css/styles.css")
   (request :header)
   (include-js "/js/modernizr-2.7.0.min.js")
   (google-analytics)])

(defn page-body [request]
  [:body
   (if (request :navbar) (navbar request))
   [:div#wrap [:div.container
               (if (request :heading) (heading (request :heading)))
               (request :body)]]
   (if (not (request :hide-footer)) (footer))

   (include-js
    "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js")
   [:script "window.jQuery || document.write('"
    "<script src=\"/js/jquery-1.10.2.min.js\"><\\/script>');"]
   (include-js "/js/bootstrap-3.0.1.min.js"
               "//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"
               "/js/main.js"
               "/js/moment.min.js")
   (request :javascript)])

(defn page
  ([contents]         (page {:params {}} contents))
  ([request contents] (let [data (merge request contents)]
                        (html5 {:lang "en" :class "no-js"}
                               (page-head data) (page-body data)))))

;; ---------
;; ## Errors

;; ### 404 - File Not Found
;; The bog standard invalid URL response.
(defn page-404 []
  {:status 404
   :body (page {:title "Page Not Found",
                :navbar {:search false}
                :body [:div.row
                       [:div.col-lg-12.text-center
                        [:div.jumbotron.errortron
                         [:h1 "404 :("]
                         [:p "Sorry, I couldn't find the page you're after."]
                         [:p
                          [:a.btn.btn-lg.btn-danger {:href "/"}
                           "I want to complain!"] " "
                          [:a.btn.btn-lg.btn-success {:href "/"}
                           "Just take me home"]]]]]})})

;; ### 500 - Internal Server Error.
;;
;; Our "oops" page. An exception can optionally be passed as an
;; argument to it, otherwise, a generic apology message is created.
(defn page-500 [exception]
  (page {:title "Woops!",
         :navbar {:search false}
         :body (list [:div.row
                      [:div.col-lg-12.text-center
                       [:div.jumbotron.errortron
                        [:h1 "500 :("]
                        [:p (if util/debug?
                              (.toString exception)
                              "Sorry, I couldn't show the page you're after!")]
                        (if (not util/debug?)
                          [:p
                           [:a.btn.btn-lg.btn-danger {:href "/"}
                            "I want to complain!"] " "
                           [:a.btn.btn-lg.btn-success {:href "/"}
                            "Just take me home"]])]]]
                     (if util/debug?
                       [:div.row
                        [:div.col-lg-12
                         [:pre.stack-trace
                          (map (fn [line] (str line "\n"))
                               (.getStackTrace exception))]]]))}))
