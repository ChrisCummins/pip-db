(ns pip-db.controllers.search
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.models.search :as model]
            [pip-db.views.search :as view]))

(defn search [query results]
  (view/search query results))

(defroutes routes
  (GET "/s" {params :params}
       (search (get params "q") (model/query params))))
