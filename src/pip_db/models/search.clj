(ns pip-db.models.search
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.db :as db]))

(defn split-args [words]
  (when (and words (not (str/blank? words)))
    (str/split (str/trim words) #" +")))

(defn conditionals [params]
  (let [id      (str (get params "id"))
        q       (split-args (get params "q"))
        q_eq    (get params "q_eq")
        q_any   (split-args (get params "q_any"))
        q_ne    (split-args (get params "q_ne"))
        q_s     (get params "q_s")
        q_l     (get params "q_l")
        m       (get params "m")]

    (AND
     (EQ {:field "id" :value id})       ; Match specific record ID
     (for [word q]                      ; Match all keywords
       (EQ {:field "names" :value word}))
     (EQ {:field "names" :value q_eq})  ; Match exact phrase
     (for [word q_any]                  ; Match any keywords
       (EQ {:field "names" :value word}))
     (for [word q_ne]                   ; Exclude keywords
       (NE {:field "names" :value word}))
     (EQ {:field "source" :value q_s})
     (EQ {:field "location" :value q_l})
     (EQ {:field "method" :value m}))))

;; ### Query components

;; We can now take a query map and use this to generate a SQL
;; query. If the query map is empty, then we return an empty string.
(defn query [params]
  (let [conditions (conditionals params)]
    (if (str/blank? conditions)
      ""
      (str "SELECT " db/records-columns " FROM "
           db/records-table " WHERE " conditions))))

(defn search [params]
  (db/search (query params) params))
