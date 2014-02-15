(ns pip-db.controllers.record
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.record :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.record :as view]
            [pip-db.views.error :as error]))

(defn record [id]
  (let [data (model/record id)]
    (if data
      (view/record data)
      (error/status-404))))

(defroutes routes
  (GET "/record/:id" [id] (record id)))
