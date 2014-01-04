(ns pip-db.query
  (:require [clojure.string :as str]))

(defn strip-conditions [conditions]
  (filter (fn [condition] (not (= "()" condition))) conditions))

(defn compound-conditional [join conditions]
  (str "(" (str/join join (strip-conditions conditions)) ")"))

(defn EQ [condition]
  (str "LOWER(" (condition :field) ") LIKE LOWER('%" (condition :value) "%')"))

(defn NE [condition]
  (str "LOWER(" (condition :field)
       ") NOT LIKE LOWER('%"
       (condition :value) "%')"))

(defn AND [conditions]
  (compound-conditional " AND " (doall conditions)))

(defn OR [conditions]
  (compound-conditional " OR " (doall conditions)))
