(ns pip-db.models.record
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(defn query-string [id]
  (str "SELECT * FROM records WHERE id='" id "'"))

(defn record [id]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      [(query-string id)]
      (when results (first (doall results))))))
