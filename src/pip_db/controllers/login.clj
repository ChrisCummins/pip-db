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

(defroutes routes
  (GET "/login" [] (login))
  (POST "/login" [user pass action]
        (if (and (not (str/blank? user))
                 (not (str/blank? pass)))
          (login (if (= action "register")
                   (model/attempt-register user pass)
                   (model/attempt-login user pass)))
          (login))))
