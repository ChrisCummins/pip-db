(ns pip-db.controllers.login
  (:require [clojure.string :as str]
            [pip-db.util :as util]
            [pip-db.views.login :as view]
            [pip-db.models.login :as model]))

(defn do-login [user pass]
  (if (model/attempt-login user pass)
    {:status 200 :headers {"Location" (util/referer)}
     :cookies (model/user-cookie user)} ; Correct login details
    {:status 401}))                     ; Invalid login details

(defn do-register [user pass]
  (if (model/attempt-register user pass)
    (do-login user pass)                ; User registered
    {:status 409}))                     ; User already exists

(defn form-is-filled [user pass]
  (and (not (str/blank? user)) (not (str/blank? pass))))

(defn login
  ([] (view/login))
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
