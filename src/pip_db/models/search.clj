(ns pip-db.models.search
  (:require [clojure.java.jdbc :as sql]
            [clojure.string :as str]))

(defn condition [field value]
  (str "LOWER(" field ") LIKE LOWER('%" value "%')"))

(defn composite [join & conditions]
  (str "(" (str/join (str " " join " ") conditions) ")"))

(defn build-query [params]
  (str "SELECT id,name,source,organ,pi FROM records WHERE "
       (composite "AND"
                  (composite "OR"
                             (condition "name" (get params "q"))
                             (condition "alt_name" (get params "q"))))))

(defn query [params]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/with-query-results results
      [(build-query params)]
      (if (= results nil)
        nil (doall results)))))
