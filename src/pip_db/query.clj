;; # Structured SQL queries
;;
;; The name *Protein Isoelectric Point Database* should give the
;; reader an indication that *databases* play an important role. In
;; order to work effectively with databases, we need a way to perform
;; queries of the data, in a structured and beautiful manner.
;;
;; The purpose of this file is to define a way in which we can
;; structure our SQL select statements in a way which is pleasing and
;; LISP-like. To do this, we define two components, which can be
;; combined to form a tree like structure, called a *condition tree*.
;; Each node within the tree can either be a **match** condition or a
;; **compound** condition.
(ns pip-db.query
  (:require [clojure.string :as str]))

;; The regular expression literal to match empty conditions. There can
;; be two types of empty conditions: empty text conditions and empty
;; compound conditions, which manifest themselves as `""` and `"()"`,
;; respectively.
(def re-empty-condition #" *\(? *\)?\ *")

;; We need a way to strip empty conditions from a list of conditions.
(defn strip-conditions [conditions]
  (filter #(not (re-matches re-empty-condition %)) conditions))

;; ## Matching conditions
;;
;; A match condition is used to test some kind of equality within a
;; field, for example, to test if "foo" == "bar". A compound condition
;; is used to
(defn match-condition [condition]
  (if-not (str/blank? (condition :value))
    (str "(LOWER(" (condition :field) ") "
         (if (condition :not) "NOT ") "LIKE "
         "LOWER('%" (condition :value) "%'))") ""))

;; ### Field is equals
(defn EQ [condition]
  (match-condition condition))

;; ### Field is not equals
(defn NE [condition]
  (match-condition (assoc condition :not true)))

;; ## Compound Conditions
;;
;; A compound condition is used to join multiple conditions in a
;; logical manner. For example, "foo" == "bar" *and* "baz" ==
;; "car". If only one condition is provided, this has no effect.
(defn compound-condition [join conditions]
  (let [stripped (strip-conditions conditions)]
    (if-not (zero? (count stripped))
      (str "(" (str/join join stripped) ")") "")))

;; ### All conditions must match
(defn AND [& conditions]
  (compound-condition " AND " (flatten conditions)))

;; ### One or more conditions must match
(defn OR [& conditions]
  (compound-condition " OR " (flatten conditions)))
