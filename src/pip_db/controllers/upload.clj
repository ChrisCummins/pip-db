(ns pip-db.controllers.upload
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.views.upload :as view]))

(defn upload []
  (view/upload))

(defroutes routes
  (GET "/upload" [] (upload)))
