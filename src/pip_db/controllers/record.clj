(ns pip-db.controllers.record
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.record :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.record :as view]
            [pip-db.views.error :as error]))

(defn handler [request]
  (let [data (model/record ((request :params) :id))]
    (if data
      (view/record (assoc request :results data))
      (error/status-404))))
