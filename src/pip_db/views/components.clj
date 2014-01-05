(ns pip-db.views.components
  (:use [pip-db.resources :only (resource)]))

(defn inline-css [path]
  [:style (resource path)])

(defn inline-js [path]
  [:script (resource path)])
