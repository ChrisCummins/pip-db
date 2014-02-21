(ns pip-db.models.search
  (:use [pip-db.query :only (AND OR EQ NE)])
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]
            [pip-db.db :as db]))

(def max-no-of-returned-records 20)

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
;;
;; First off, we must define the table within which we are performing
;; look-ups.
(def query-table "records")

;; We can now take a query map and use this to generate a SQL
;; query. If the query map is empty, then we return an empty string.
(defn query [params]
  (let [conditions (conditionals params)]
    (if (str/blank? conditions)
      ""
      (str "SELECT id,names,ec,source,location,mw_min,mw_max,sub_no,sub_mw,"
           "iso_enzymes,pi_min,pi_max,pi_major,temp_min,temp_max,method,"
           "ref_full,ref_abstract,ref_pubmed,ref_taxonomy,ref_sequence,notes,"
           "created_at FROM " query-table " WHERE " conditions))))

;; Construct a search results map from a set of search parameters and
;; a list of matching records.
(defn search-response [params matching-records no-of-records]
  (let [returned-records (take max-no-of-returned-records matching-records)]
    {:query                      params
     :no_of_records              no-of-records
     :no_of_matches              (count matching-records)
     :no_of_returned_records     (count returned-records)
     :max_no_of_returned_records max-no-of-returned-records
     :records                    returned-records}))

(defn search [params]
  (search-response params (db/search (query params)) (db/no-of-records)))
