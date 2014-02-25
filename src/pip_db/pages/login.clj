(ns pip-db.pages.login
  (:require [clojure.string :as str]
            [clojure.java.jdbc :as sql]
            [crypto.password.bcrypt :as crypto]
            [pip-db.db :as db]
            [pip-db.ui :as ui]
            [pip-db.util :as util]))

;; ## View

(defn alert [id message class]
  [:div.text-center {:id id :class class :style "display:none;"}
   [:strong message]])

(defn error-alert [id message]
  (alert id message "login-fail"))

(defn success-alert [id message]
  (alert id message "login-success"))

(defn view []
  (ui/page
   {:title "Sign in"
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

;; ## Model

(defn get-user [user]
  (db/with-connection-results-query results
    [(str "SELECT * FROM users WHERE email='" user "'")]
    (when-not (nil? results) (first (doall results)))))

(defn get-hash [plaintext]
  (crypto/encrypt plaintext))

(defn hashes-match? [plaintext hash]
  (crypto/check plaintext hash))

(defn credentials-are-valid? [user password]
  (hashes-match? password ((get-user user) :pass)))

(defn add-account [user password]
  (db/with-connection
    (sql/insert-values :users
                       [:email :pass :user_type_id]
                       [user (get-hash password) 1]))
  (str "Add user accound - user: " user " pass: " (get-hash password)))

(defn attempt-register [user password]
  (if-not (get-user user)
    (do (add-account user password) true)
    false))

(defn attempt-login [user password]
  (if (and (get-user user)
           (credentials-are-valid? user password))
    true false))

(defn user-cookie [user]
  {"pip-db" {:value user :max-age util/seconds-in-a-week}})

;; ## Controller

(defn do-login [user pass]
  (if (attempt-login user pass)
    {:status 200 :headers {"Location" (util/referer)}
     :cookies (user-cookie user)} ; Correct login details
    {:status 401}))                     ; Invalid login details

(defn do-register [user pass]
  (if (attempt-register user pass)
    (do-login user pass)                ; User registered
    {:status 409}))                     ; User already exists

(defn form-is-filled [user pass]
  (and (not (str/blank? user)) (not (str/blank? pass))))

(defn login
  ([] (view))
  ([user pass action]
     (if (form-is-filled user pass)
       (if (= action "register")
         (do-register user pass)
         (do-login user pass))
       {:status 400})))                 ; Missing form details

(defn POST [request]
  (let [params (request :params)
        user   (params "user")
        pass   (params "pass")
        action (params "action")]
    (login user pass action)))

(defn GET [request] (login))
