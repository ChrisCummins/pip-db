(ns pip-db.models.search
  (:require [clojure.java.jdbc :as sql]))

(defn query [params]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      [(str "SELECT id,name,source,organ,pi FROM records WHERE "
            "LOWER(name) LIKE LOWER('%" (get params "q") "%')")]
      (if (= results nil)
        nil (doall results)))))
