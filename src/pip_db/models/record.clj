(ns pip-db.models.record
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.models.search :as search]
            [pip-db.util :as util]))

;; Fetch the record data for the given ID.
(defn record [id]
  (search/search {"id" id}))
