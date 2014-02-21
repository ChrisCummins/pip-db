(ns pip-db.controllers.record
  (:require [pip-db.models.record :as model]
            [pip-db.views.advanced :as advanced]
            [pip-db.views.record :as view]
            [pip-db.views.error :as error]))

(defn handler [request]
  (let [data (model/record ((request :params) :id))]
    (if (pos? (data :no_of_matches))
      (view/record (assoc request :results data))
      (error/status-404))))
