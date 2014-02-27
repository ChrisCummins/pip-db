(ns pip-db.yaps
  (:use [clojure.core :only (slurp)])
  (:require [clojure.data.json :as json]
            [pip-db.db :as db]))

(defn parse [file]
  (let [json    (json/read-str (slurp file))
        records (json "Records")]
    (str (apply db/add-records records))))
