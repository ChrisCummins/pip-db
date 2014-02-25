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
  (if (condition :exact)
    (str "\"" (condition :field) "\"='" (condition :value) "'")
    (str "(LOWER(\"" (condition :field) "\") "
         (if (condition :not) "NOT ")
         "LIKE LOWER('%" (condition :value) "%'))")))
;;
;; A `numeric-condition` tests a field for a precise integer field,
;; e.g. "foo" == 5.
(defn numeric-condition [condition]
  (if (util/is-number? (condition :value))
    (str "\"" (condition :field) "\"" (condition :operator) (condition :value))))

;; ### Field is equals
(defn EQ [condition]
  (cond
   (str/blank?           (condition :value)) ""
   (condition :numeric)  (numeric-condition (assoc condition :operator "="))
   :else                 (string-condition condition)))

;; ### Field is not equals
(defn NE [condition]     (EQ (assoc condition :not true)))

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
    (if (pos? (count stripped))
      (str "(" (str/join join stripped) ")") "")))

;; ### All conditions must match
(defn AND [& conditions] (compound-condition " AND " (flatten conditions)))

;; ### One or more conditions must match
(defn OR [& conditions]  (compound-condition " OR " (flatten conditions)))

;; ## Structured queries

(defn split-args [words]
  (when (and words (not (str/blank? words)))
    (str/split (str/trim words) #" +")))

(defn params->query [params]
  (let [id      (str (get params "id"))
        q       (split-args (get params "q"))
        q_eq    (get params "q_eq")
        q_any   (split-args (get params "q_any"))
        q_ne    (split-args (get params "q_ne"))
        q_s     (get params "q_s")
        q_l     (get params "q_l")
        m       (get params "m")
        pi_l    (get params "pi_l")
        pi_h    (get params "pi_h")
        mw_l    (str (util/str->num (get params "mw_l")))
        mw_h    (str (util/str->num (get params "mw_h")))
        t_l     (str (util/str->num (get params "t_l")))
        t_h     (str (util/str->num (get params "t_h")))
        ec1     (str (util/str->int (get params "ec1")))
        ec2     (str (util/str->int (get params "ec2")))
        ec3     (str (util/str->int (get params "ec3")))
        ec4     (str (util/str->int (get params "ec4")))]

    (AND
     (EQ {:field "id" :value id :exact true}) ; Match specific record ID
     (for [word q]                      ; Match all keywords
       (EQ {:field "Protein-Names" :value word}))
     (EQ {:field "Protein-Names" :value q_eq})  ; Match exact phrase
     (for [word q_any]                  ; Match any keywords
       (EQ {:field "Protein-Names" :value word}))
     (for [word q_ne]                   ; Exclude keywords
       (NE {:field "Protein-Names" :value word}))
     (EQ {:field "Source" :value q_s})
     (EQ {:field "Location" :value q_l})
     (EQ {:field "Method" :value m})
     (GTE {:field "real_pi_min" :value pi_l})
     (LTE {:field "real_pi_max" :value pi_h})
     (GTE {:field "real_mw_min" :value mw_l})
     (LTE {:field "real_mw_max" :value mw_h})
     (GTE {:field "real_temp_min" :value t_l})
     (LTE {:field "real_temp_max" :value t_h})
     (EQ {:field "real_ec1" :value ec1 :numeric true})
     (EQ {:field "real_ec2" :value ec2 :numeric true})
     (EQ {:field "real_ec3" :value ec3 :numeric true})
     (EQ {:field "real_ec4" :value ec4 :numeric true}))))
