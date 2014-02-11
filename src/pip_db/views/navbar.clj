(ns pip-db.views.navbar
  (:require [pip-db.views.components :as components]))

(defn navbar-search [data]
  [:div.search.navbar-search (components/search-bar data)])

(defn navbar-user [data]
  [:ul.nav.navbar-nav.navbar-right
   (if (data :session)
     [:li.dropdown
      [:a#themes.dropdown-toggle {:href "#" :data-toggle "dropdown"}
       ((data :session) :user) [:span.caret]]
      [:ul.dropdown-menu
       [:li [:a {:href "/admin/setup" :tabindex "-1"} "Run initial setup"]]
       [:li [:a {:href "/upload" :tabindex "-1"} "Upload new data"]]
       [:li [:a {:href "/logout" :tabindex "-1"} "Preferences"]]
       [:li.divider]
       [:li [:a {:href "/logout" :tabindex "-1"} "Log out"]]]]
     [:li [:a {:href "/login"} "Login"]])])

(defn html [data]
  [:div.navbar.navbar-fixed-top

   {:class (if (data :login-only)
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
     [:a.navbar-brand {:href "/"} components/small-logo "pip-db"]]

    [:div.navbar-collapse.collapse
     (if (data :search) (navbar-search data))
     (if (not (data :hide-user)) (navbar-user data))]]])
