(ns pip-db.test.views.navbar
  (:use clojure.test)
  (:require [pip-db.views.navbar :as dut]))

(deftest navbar-search
  (testing "No search text"
    (is (= (dut/navbar-search {})
           '([:div.navbar-search.col-sm-7.col-md-7
              [:form.navbar-form {:action "/s", :role "search"}
               [:div.input-group
                [:input#q.form-control {:name "q", :type "text",
                                        :value nil, :autocomplete "off"}]
                [:div.input-group-btn
                 [:button.btn.btn-success {:type "submit"} "Search"]]]]]
               [:ul.nav.navbar-nav
                [:li
                 [:div.navbar-button-group
                  [:div.navbar-button
                   [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]]))))

  (testing "Search text"
    (is (= (dut/navbar-search {:search-text "foo"})
           '([:div.navbar-search.col-sm-7.col-md-7
              [:form.navbar-form {:action "/s", :role "search"}
               [:div.input-group
                [:input#q.form-control {:name "q", :type "text",
                                        :value "foo", :autocomplete "off"}]
                [:div.input-group-btn
                 [:button.btn.btn-success {:type "submit"} "Search"]]]]]
               [:ul.nav.navbar-nav
                [:li
                 [:div.navbar-button-group
                  [:div.navbar-button
                   [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]])))))

(deftest html
  (testing "No data"
    (is (= (dut/html {})
           [:div.navbar.navbar-fixed-top {:class "navbar-default"}
            [:div.container
             [:div.navbar-header
              [:button.navbar-toggle {:data-target "navbar-collapse",
                                      :data-toggle "collapse", :type "button"}
               [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
              [:a.navbar-brand {:href "/"} "pip-db"]]
             [:div.navbar-collapse.collapse nil
              [:ul.nav.navbar-nav.navbar-right
               [:li [:a {:href "/login"} "Login"]]]]]])))

  (testing "Login only"
    (is (= (dut/html {:login-only true})
           [:div.navbar.navbar-fixed-top {:class "navbar-invisible"}
            [:div.container
             [:div.navbar-header
              [:button.navbar-toggle {:data-target "navbar-collapse",
                                      :data-toggle "collapse",
                                      :type "button"}
               [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
              [:a.navbar-brand {:href "/"} "pip-db"]]
             [:div.navbar-collapse.collapse nil
              [:ul.nav.navbar-nav.navbar-right
               [:li [:a {:href "/login"} "Login"]]]]]])))

  (testing "Search bar"
    (is (= (dut/html {:search true})
           [:div.navbar.navbar-fixed-top {:class "navbar-default"}
            [:div.container
             [:div.navbar-header
              [:button.navbar-toggle {:data-target "navbar-collapse",
                                      :data-toggle "collapse", :type "button"}
               [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
              [:a.navbar-brand {:href "/"} "pip-db"]]
             [:div.navbar-collapse.collapse
              '([:div.navbar-search.col-sm-7.col-md-7
                 [:form.navbar-form {:action "/s", :role "search"}
                  [:div.input-group
                   [:input#q.form-control {:name "q", :type "text",
                                           :value nil, :autocomplete "off"}]
                   [:div.input-group-btn
                    [:button.btn.btn-success {:type "submit"} "Search"]]]]]
                  [:ul.nav.navbar-nav
                   [:li
                    [:div.navbar-button-group
                     [:div.navbar-button
                      [:a.btn.btn-primary {:href "/advanced"} "Advanced"]]]]])
              [:ul.nav.navbar-nav.navbar-right
               [:li [:a {:href "/login"} "Login"]]]]]])))

  (testing "Session information"
    (is (= (dut/html {:session {:user "foo"}})
           [:div.navbar.navbar-fixed-top {:class "navbar-default"}
            [:div.container
             [:div.navbar-header
              [:button.navbar-toggle {:data-target "navbar-collapse",
                                      :data-toggle "collapse", :type "button"}
               [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]
              [:a.navbar-brand {:href "/"} "pip-db"]]
             [:div.navbar-collapse.collapse nil
              [:ul.nav.navbar-nav.navbar-right
               [:li.dropdown
                [:a#themes.dropdown-toggle {:href "#", :data-toggle "dropdown"}
                 "foo" [:span.caret]]
                [:ul.dropdown-menu
                 [:li [:a {:href "/admin/setup", :tabindex "-1"}
                       "Run initial setup"]]
                 [:li [:a {:href "/upload", :tabindex "-1"}
                       "Upload new data"]]
                 [:li [:a {:href "/logout", :tabindex "-1"}
                       "Preferences"]]
                 [:li.divider]
                 [:li [:a {:href "/logout", :tabindex "-1"}
                       "Log out"]]]]]]]]))))
