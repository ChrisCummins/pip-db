(ns pip-db.views.navbar
  (:use [pip-db.views.components]))

(defn navbar-search [data]
  (list [:div.search.navbar-search
         [:form.navbar-form {:action "/s" :role "search"}
          [:div.input-group
           [:input#q.form-control {:name "q"
                                   :type "text"
                                   :value (data :search-text)
                                   :autocomplete "off"}]
           [:div.input-group-btn
            [:button.btn.btn-success {:type "submit"} "Search"]
            [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]]))

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
     [:a.navbar-brand {:href "/"}
      [:img {:src "/img/logo-32x32.png"
             :alt-text "pip-db"
             :style "margin-right:8px;width:32px;height:32px"}]
      "pip-db"]]

    [:div.navbar-collapse.collapse
     (if (data :search) (navbar-search data))
     (if (not (data :hide-user)) (navbar-user data))]]])
