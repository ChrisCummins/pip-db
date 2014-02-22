(ns pip-db.controllers.download
  (:require [pip-db.models.search :as model]
            [pip-db.views.download :as view]))

;; Perform a search from the given request map and wrap the results
;; into a `:results` key.
(defn search-results [request]
  (assoc request :results (model/search (request :params))))

(defn GET [request] (view/download request)
  (view/download (search-results request)))
