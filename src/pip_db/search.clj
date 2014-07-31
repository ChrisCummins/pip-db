;; pip-db search functionality
(ns pip-db.search
  (:require [clojure.string :as str]
            [pip-db.blast :as blast]
            [pip-db.util :as util]
            [pip-db.db :as db]))

(def max-no-of-returned-records 150)

;; Perform a pip-db search and wrap the results in a search response
;; map. Note as an implementation detail, the `merge` function means
;; that properties are returned in the reverse order to as included
;; here.
(defn search [request]
  (let [sequence               ((request :params) "seq")
        blast?                 (not (str/blank? sequence))
        params                 (request :params)
        headers                (request :headers)
        start?                 (and (contains? params "start")
                                    (util/is-number? (params "start"))
                                    (>= (util/str->int (params "start")) 0))
        start                  (if start? (util/str->int (params "start")) 0)
        include-query-terms?   (not (= (headers "x-pip-db-query-terms") "None"))
        include-records?       (not (= (headers "x-pip-db-records")     "None"))
        matching-records       (if blast? (blast/search request) (db/search request))
        no-of-records-searched (if blast? (blast/no-of-records)  (db/no-of-records))]
    (merge
     (if include-records?
       (let [returned-records (take max-no-of-returned-records (drop start matching-records))]
          {:Returned-Records-Starting-At start
           :No-Of-Records-Returned       (count returned-records)
           :Records                      returned-records}))
     {:No-Of-Records-Matched             (count matching-records)
      :Max-No-of-Returned-Records        max-no-of-returned-records
      :No-Of-Records-Searched            no-of-records-searched}
     (if include-query-terms?
       {:Query-Terms                     (dissoc params "seq_name")}))))
