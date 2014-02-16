(ns pip-db.views.login
  (:require [pip-db.util :as util])
  (:use [pip-db.views.page :only (page)]))

(defn error-alert [id message]
  [:div.alert.alert-danger {:id id :style "display:none;"} [:strong message]])

(defn success-alert [id message]
  [:div.alert.alert-success {:id id :style "display:none;"} [:strong message]])

(defn login []
  (page {:title "Sign in"
         :navbar {:hide-user true}
         :header (util/inline-css "/css/login.css")
         :body [:form.form-signin {:method "post" :action "/login"}
                [:h2.form-signin-heading "Login"]
                [:div.form-group
                 [:input.form-control {:name "user" :type "text"
                                       :autocomplete "off"
                                       :placeholder "Email address"}]]
                [:div.form-group
                 [:input.form-control {:name "pass" :type "password"
                                       :placeholder "Password"}]]
                [:label.checkbox [:input {:type "checkbox"
                                          :value "remember-me"}
                                  "Remember me"]]

                [:div {:style "display:table;width:100%;margin-bottom:1em;"}
                 [:div {:style (str "display: table-cell; "
                                    "width: 50%; padding-right: 8px;")}
                  [:button.btn.btn-lg.btn-block.btn-primary
                   {:name "action" :type "button" :value "register"}
                   "Register"]]
                 [:div {:style (str "display: table-cell; "
                                    "width: 50%; padding-left: 8px;")}
                  [:button.btn.btn-lg.btn-block.btn-success
                   {:name "action" :value "login"}
                   "Sign in"]]]
                (success-alert "200" "Log in successful. Redirecting...")
                (error-alert "403" "Incorrect details")
                (error-alert "500" "Unknown Error")]
         :javascript (util/inline-js "/js/login.inline.js")}))
