(ns pip-db.models.search
  (:use [pip-db.query :only (AND OR EQ NE GTE LTE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.db :as db]
            [pip-db.util :as util]))

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
        m       (get params "m")
        pi_l    (get params "pi_l")
        pi_h    (get params "pi_h")
        mw_l    (str (util/str->num (get params "mw_l")))
        mw_h    (str (util/str->num (get params "mw_h")))]

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
     (EQ {:field "method" :value m})
     (GTE {:field "real_pi_min" :value pi_l})
     (LTE {:field "real_pi_max" :value pi_h})
     (GTE {:field "real_mw_min" :value mw_l})
     (LTE {:field "real_mw_max" :value mw_h}))))

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
