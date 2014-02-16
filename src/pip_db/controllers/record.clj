(ns pip-db.controllers.record
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.record :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.record :as view]
            [pip-db.views.error :as error]))

(defn do-record [id request]
  (let [data (model/record id)]
    (if data
      (view/record (merge data request))
      (error/status-404))))

(defn handler [request]
  (do-record ((request :params) :id) request))
