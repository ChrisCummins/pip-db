(ns pip-db.test.views.footer
  (:use [clojure.test])
  (:require [pip-db.views.footer :as dut]))

(deftest html
  (testing "HTML contents"
    (is (= (dut/html)
           [:div.navbar.navbar-default.navbar-bottom
            [:div.container
             [:div.navbar-header
              [:button.navbar-toggle
               {:data-target "navbar-collapse",
                :data-toggle "collapse",
                :type "button"}
               [:span.icon-bar] [:span.icon-bar] [:span.icon-bar]]]
             [:div.navbar-collapse.collapse
              [:ul.nav.navbar-nav
               [:li [:a {:href "#"} "About"]]
               [:li [:a {:href "#"} "Contact"]]]
              [:ul.nav.navbar-nav.navbar-right
               [:li [:a {:href "#"} "Terms"]]
               [:li [:a {:href "#"} "Privacy"]]]]]]))))
