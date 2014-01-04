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

(defn match-condition [condition]
  (if (condition :value)
    (str "(LOWER(" (condition :field) ") "
         (if (condition :not) "NOT ") "LIKE "
         "LOWER('%" (condition :value) "%'))") ""))

(defn EQ [condition]
  (match-condition condition))

(defn NE [condition]
  (match-condition (assoc condition :not true)))

(defn AND [conditions]
  (compound-conditional " AND " (doall conditions)))

(defn OR [conditions]
  (compound-conditional " OR " (doall conditions)))
