(ns pip-db.controllers.record
  (:require [pip-db.models.record :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.record :as view]
            [pip-db.views.error :as error]))

(defn handler [request]
  (let [data (model/record ((request :params) :id))]
    (if (> (data :no_of_matches) 0)
      (view/record (assoc request :results data))
      (error/status-404))))
