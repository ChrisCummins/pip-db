(ns pip-db.views.login
  (:require [pip-db.util :as util])
  (:use [pip-db.views.page :only (page)]))

(defn alert [id message class]
  [:div.text-center {:id id :class class :style "display:none;"}
   [:strong message]])

(defn error-alert [id message]
  (alert id message "login-fail"))

(defn success-alert [id message]
  (alert id message "login-success"))

(defn login []
  (page {:title "Sign in"
         :navbar {:hide-user true}
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
                [:div#messages
                 (success-alert "200" "Log in successful. Redirecting...")
                 (error-alert "403" "Incorrect details")
                 (error-alert "500" "Unknown Error")
                 (error-alert "invalid" "Please fill in the form")
                 [:div#registration
                  {:style "display:none;"}
                  "Unfortunately at this time account registration is by "
                  [:strong "approval only"] ". Please "
                  [:a {:href "/contact-us"} "contact us"] " to request an "
                  "account, stating your reasons."]]]
         :javascript (util/inline-js "/js/login.inline.js")}))
