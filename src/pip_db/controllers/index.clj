(ns pip-db.controllers.index
  (:use [compojure.core :only (defroutes GET)])
  (:require [pip-db.views.index :as view]))

(defn index [params]
  (view/index params))

(defroutes routes
  (GET "/" {params :params} (index {:search-text (get params "q")})))
