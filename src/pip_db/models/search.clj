(ns pip-db.models.search
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(defn split-args [words]
  (when (and words (not (str/blank? words)))
    (str/split (str/trim words) #" +")))

(defn conditionals [params]
  (let [q       (split-args (get params "q"))
        q_eq    (get params "q_eq")
        q_any   (split-args (get params "q_any"))
        q_ne    (split-args (get params "q_ne"))
        q_s     (get params "q_s")
        q_l     (get params "q_l")
        m       (get params "m")]

    (AND
     (AND                               ; Match all keywords
      (for [word q]
        (OR
         (EQ {:field "name" :value word})
         (EQ {:field "alt_name" :value word}))))
     (OR                                ; Match exact phrase
      (EQ {:field "name" :value q_eq})
      (EQ {:field "alt_name" :value q_eq}))
     (OR                                ; Match any keywords
      (for [word q_any]
        (OR
         (EQ {:field "name" :value word})
         (EQ {:field "alt_name" :value word}))))
     (AND                               ; Exclude keywords
      (for [word q_ne]
        (AND
         (NE {:field "name" :value word})
         (NE {:field "alt_name" :value word}))))
     (EQ {:field "source" :value q_s})
     (EQ {:field "organ" :value q_l})
     (EQ {:field "method" :value m}))))

;; ### Query components
;;
;; First off, we must define the table within which we are performing
;; look-ups.
(def query-table "records")
;; Then we specify a vector of field names from within this table to
;; query.
(def query-fields ["id" "name" "source" "organ" "pi"])

(defn query [params]
  (let [conditions (conditionals params)]
    (str "SELECT " (str/join "," query-fields)
         " FROM " query-table
         (if-not (str/blank? conditions)
           (str " WHERE " (conditionals params))))))

(defn search [params]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results [(query params)]
      (let [data (apply vector (doall results))]
        {:results data
         :results-count (count data)}))))
