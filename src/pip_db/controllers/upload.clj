(ns pip-db.controllers.upload
  (:require [pip-db.models.upload :as model]
            [pip-db.views.upload :as view]))

(defn upload []
  (view/upload))

(defn GET [request]
  (view/upload request))

(defn POST [request]
  (let [file ((request :params) "f")]
    (if file
      (model/parse-json-file (model/upload-file file))
      "No file found")))
