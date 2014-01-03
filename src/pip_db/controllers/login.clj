(ns pip-db.controllers.login
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [clojure.string :as str]
            [ring.util.response :as ring]
            [pip-db.views.login :as view]
            [pip-db.models.login :as model]))

;; TODO: Accept an optional argument which displays an error message
;; to show, i.e. login credentials were invalid, username already
;; taken etc.
(defn login
  ([] (view/login))
  ([response] response))

(defn process-action [action user pass]
  (login (if (= action "register")
                   (model/attempt-register user pass)
                   (model/attempt-login user pass))))

(defn form-is-filled [user pass]
  (and (not (str/blank? user)) (not (str/blank? pass))))

(defroutes routes
  (GET "/login" [] (login))
  (POST "/login" [user pass action]
        (if (form-is-filled user pass)
          (process-action action user pass)
          (login "Missing details"))))
