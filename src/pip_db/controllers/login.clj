(ns pip-db.controllers.login
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [pip-db.util :as util]
            [pip-db.views.login :as view]
            [pip-db.models.login :as model]))

(defn do-login [user pass]
  (if (model/attempt-login user pass)
    {:status 200 :headers {"Location" (util/referer)}
     :cookies {"pip-db" {:value user}}} ; Correct login details
    {:status 401}))                     ; Invalid login details

(defn do-register [user pass]
  (if (model/attempt-register user pass)
    (do-login user pass)                ; User registered
    {:status 409}))                     ; User already exists

(defn form-is-filled [user pass]
  (and (not (str/blank? user)) (not (str/blank? pass))))

;; TODO: Accept an optional argument which displays an error message
;; to show, i.e. login credentials were invalid, username already
;; taken etc.
(defn login
  ([] (view/login))
  ([user pass action]
     (if (form-is-filled user pass)
       (if (= action "register")
         (do-register user pass)
         (do-login user pass))
       {:status 400})))                 ; Missing form details

(defroutes routes
  (GET  "/login" [] (login))
  (POST "/login" [user pass action] (login user pass action)))
