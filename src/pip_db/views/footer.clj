(ns pip-db.views.footer
  (:require [pip-db.util :as util])
  (:use [pip-db.views.components]))

(defn html []
  [:div.footer
   [:div.container.text-center
    [:hr]
    [:ul.footer-nav
     [:li "© " [:a.muted {:href "http://chriscummins.cc"
                          :target "_blank"}
                "Chris Cummins"] " " (util/current-year)]
     [:li [:a {:href "/about"} "About"]]]]])
