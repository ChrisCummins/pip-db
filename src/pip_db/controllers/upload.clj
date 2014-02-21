(ns pip-db.controllers.upload
  (:use [compojure.core :only (defroutes GET POST)])
  (:require [pip-db.models.upload :as model]
            [pip-db.views.upload :as view]))

(defn upload []
  (view/upload))

(defn get-handler [request]
  (view/upload request))

(defn post-handler [request]
  (let [file ((request :params) "f")]
    (if file
      (model/parse-json-file (model/upload-file file))
      "No file found")))
