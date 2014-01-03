(ns pip-db.models.upload
  (:use [clojure.java.io :only (copy)])
  (:import [java.io File]))

(defn upload-file [tempfile]
  (let [file (format "/tmp/%s" (tempfile :filename))]
    (copy (tempfile :tempfile) (File. file)))
  "WOO!")
