(ns pip-db.models.record
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.util :as util]))

(defn query-string [id]
  (str "SELECT * FROM records WHERE id='" id "'"))

;; Fetch the record data for the given ID.
(defn record-data [id]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      [(query-string id)]
      (when results (first (doall results))))))

(defn record [id]
  (if (integer? (util/string->int id))
    (record-data id)))
