(ns pip-db.views.footer
  (:use [pip-db.views.components]))

(defn html []
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
