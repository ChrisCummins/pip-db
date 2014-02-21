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
  (:require [clojure.string :as str]
            [pip-db.util :as util]))

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
;; field.
;;
;; The most basic kind is a `string-condition` which can be used to
;; test if a field name matches a value "foo" == "bar".
(defn string-condition [condition]
  (str "(LOWER(" (condition :field) ") "
       (if (condition :not) "NOT ") "LIKE "
       "LOWER('%" (condition :value) "%'))"))
;;
;; A `numeric-condition` tests a field for a precise integer field,
;; e.g. "foo" == 5.
(defn numeric-condition [condition]
  (if (util/is-number? (condition :value))
    (str (condition :field) (condition :operator) (condition :value))))

;; ### Field is equals
(defn EQ [condition]
  (cond
   (str/blank? (condition :value)) ""
   (condition :numeric) (numeric-condition (assoc condition :operator "="))
   :else (string-condition condition)))

;; ### Field is not equals
(defn NE [condition]
  (EQ (assoc condition :not true)))

;; ### Field is great than or equals to
(defn GTE [condition]
  (if (str/blank? (condition :value)) ""
      (numeric-condition (assoc condition :numeric true :operator ">="))))

;; ### Field is less than or equals to
(defn LTE [condition]
  (if (str/blank? (condition :value)) ""
      (numeric-condition (assoc condition :numeric true :operator "<="))))

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
