(ns pip-db.controllers.upload
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [pip-db.models.upload :as model]
            [pip-db.views.upload :as view]))

(defn upload []
  (view/upload))

(defroutes routes
  (GET "/upload" [] (upload))
  (POST "/upload" {params :params}
        (let [file (get params "f")]
          (if file
            (model/upload-file file)
            "No file found"))))
