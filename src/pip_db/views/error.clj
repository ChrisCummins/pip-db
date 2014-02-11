;; ## Server Error Pages
(ns pip-db.views.error
  (:use [pip-db.views.page :only (page)]))

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
