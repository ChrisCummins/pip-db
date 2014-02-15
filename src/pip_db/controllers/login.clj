(ns pip-db.controllers.login
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [pip-db.views.login :as view]
            [pip-db.models.login :as model]))

(defn do-login [user pass]
  (if (model/attempt-login user pass)
    "Logged in!"
    "Incorrect details!"))

(defn do-register [user pass]
  (if (model/attempt-register user pass)
    (do-login user pass)
    (str "User account already exists " user)))

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
       "Missing details")))

(defroutes routes
  (GET  "/login" [] (login))
  (POST "/login" [user pass action] (login user pass action)))
