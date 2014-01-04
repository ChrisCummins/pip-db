(ns pip-db.views.navbar
  (:use [pip-db.views.components]))

(defn navbar-search [data]
  (list [:div.navbar-search.col-sm-7.col-md-7
         [:form.navbar-form {:action "/s" :role "search"}
          [:div.input-group
           [:input#q.form-control {:name "q"
                                   :type "text"
                                   :value (data :search-text)
                                   :autocomplete "off"}]
           [:div.input-group-btn
            [:button.btn.btn-success {:type "submit"} "Search"]]]]]
        [:ul.nav.navbar-nav
         [:li
          [:div.navbar-button-group
           [:div.navbar-button
            [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]]))

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
     [:a.navbar-brand {:href "/"} "pip-db"]]

    [:div.navbar-collapse.collapse
     (if (data :search) (navbar-search data))

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
        [:li [:a {:href "/login"} "Login"]])]]]])
