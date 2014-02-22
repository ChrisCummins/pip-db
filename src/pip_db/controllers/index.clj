(ns pip-db.controllers.index
  (:require [pip-db.views.index :as view]))

(defn GET [request] (view/index request))
