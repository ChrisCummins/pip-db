(ns pip-db.views.navbar
  (:require [pip-db.views.ui :as ui]))

(defn navbar-search [request]
  [:div.search.navbar-search (ui/search-bar request)])

(defn navbar-user [navbar]
  [:ul.nav.navbar-nav.navbar-right
   (if (navbar :session)
     [:li.dropdown
      [:a#themes.dropdown-toggle {:href "#" :data-toggle "dropdown"}
       ((navbar :session) :user) [:span.caret]]
      [:ul.dropdown-menu
       [:li [:a {:href "/admin/setup" :tabindex "-1"} "Run initial setup"]]
       [:li [:a {:href "/upload" :tabindex "-1"} "Upload new data"]]
       [:li [:a {:href "/logout" :tabindex "-1"} "Preferences"]]
       [:li.divider]
       [:li [:a {:href "/logout" :tabindex "-1"} "Log out"]]]]
     [:li [:a {:href "/login"} "Login"]])])

(defn html [request]
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
       [:a.navbar-brand {:href "/"} ui/small-logo "pip-db"]]

      [:div.navbar-collapse.collapse
       (if (navbar :search) (navbar-search request))
       (if (not (navbar :hide-user)) (navbar-user navbar))]]]))
