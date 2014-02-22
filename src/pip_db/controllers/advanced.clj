(ns pip-db.controllers.advanced
  (:require [pip-db.views.advanced :as view]))

;; Serve an advanced search page.
(defn GET [request] (view/advanced request))
