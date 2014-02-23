(ns pip-db.models.upload
  (:use [clojure.core :only (slurp)]
        [clojure.java.io :only (copy)]
        [clojure-csv.core :only (parse-csv)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [clojure.data.json :as json]
            [pip-db.db :as db])
  (:import [java.io File]))

(defn upload-file [tempfile]
  (let [file (format "/tmp/%s" (tempfile :filename))]
    (copy (tempfile :tempfile) (File. file))
    file))

(defn parse-json-file [file]
  (let [json    (json/read-str (slurp file))
        records (json "Records")]
    (apply db/add-records records)
    "Done."))
