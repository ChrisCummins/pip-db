(ns pip-db.query
  (:require [clojure.string :as str]))

;; The regular expression literal to match empty conditions. There can
;; be two types of empty conditions: empty text conditions and empty
;; compound conditions, which manifest themselves as "" and "()",
;; respectively.
(def re-empty-condition #" *\(? *\)?\ *")

(defn strip-conditions [conditions]
  (filter (fn [x] (not (re-matches re-empty-condition x))) conditions))

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
