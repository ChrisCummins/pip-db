;; ## Server Error Pages
(ns pip-db.views.error
  (:use [pip-db.views.page :only (page)])
  (:require [clojure.string :as str]
            [pip-db.util :as util]))

;; ### 404 - File Not Found
;; The bog standard invalid URL response.
(defn status-404 []
  (page {:title "Page Not Found",
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
                    "Just take me home"]]]]]}))

;; ### 500 - Internal Server Error.
;;
;; Our "oops" page. An exception can optionally be passed as an
;; argument to it, otherwise, a generic apology message is created.
(defn status-500 [exception]
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
