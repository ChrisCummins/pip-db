(ns pip-db.models.record
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [pip-db.db :as db]
            [pip-db.util :as util]))

(defn query [id]
  (let [fields (apply util/keys->quoted-str db/public-record-fields)]
    (str "SELECT " fields " FROM records WHERE id='" id "'")))

;; Fetch the record data for the given ID.
(defn record [id]
  (db/search (query id) {"id" id}))
