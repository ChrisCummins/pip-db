(ns pip-db.views.components
  (:use [hiccup.core :only (html)]
        [hiccup.page :only (html5 include-css include-js)]))

(defn include-navbar []
  [:div.navbar.navbar-fixed-top.navbar-default
   [:div.container
    [:div.navbar-header
     [:button.navbar-toggle {:type "button"
                             :data-toggle "collapse"
                             :data-target "navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]
     [:a.navbar-brand {:href "/"} "pip-db"]]

    [:div.navbar-collapse.collapse
     [:div.navbar-search.col-sm-7.col-md-7
      [:form.navbar-form {:action "/s" :role "search"}
       [:div.input-group
        [:input#q.form-control {:name "q"
                                :type "text"
                                :value ""
                                :autocomplete "off"}]
        [:div.input-group-btn
         [:button.btn.btn-success {:type "submit"} "Search"]]]]]

      [:ul.nav.navbar-nav
       [:li
        [:div.navbar-button-group
         [:div.navbar-button
          [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]]
      [:ul.nav.navbar-nav.navbar-right
       [:li.dropdown
        [:a#themes.dropdown-toggle {:href "#" :data-toggle "dropdown"}
         "User" [:span.caret]]
        [:ul.dropdown-menu
         [:li [:a {:href "/admin/setup" :tabindex "-1"} "Run initial setup"]]
         [:li [:a {:href "/admin/setup" :tabindex "-1"} "Upload new data"]]
         [:li [:a {:href "/admin/setup" :tabindex "-1"} "Preferences"]]
         [:li.divider]
         [:li [:a {:href "/logout" :tabindex "-1"} "Log out"]]]]]]]])

(defn include-footer []
  [:div.navbar.navbar-default.navbar-bottom
   [:div.container
    [:div.navbar-header
     [:button.navbar-toggle {:type "button"
                             :data-toggle "collapse"
                             :data-target "navbar-collapse"}
      [:span.icon-bar]
      [:span.icon-bar]
      [:span.icon-bar]]]
    [:div.navbar-collapse.collapse
     [:ul.nav.navbar-nav
      [:li [:a {:href "#"} "About"]]
      [:li [:a {:href "#"} "Contact"]]]
     [:ul.nav.navbar-nav.navbar-right
      [:li [:a {:href "#"} "Terms"]]
      [:li [:a {:href "#"} "Privacy"]]]]]])
