;; # Database Interface
;;
;; This namespace defines the interface and API for the database
;; back-end of pip-db.
(ns pip-db.db
  (:require [clojure.java.jdbc :as sql]))

;; SHA1 implementation
;;
;; See: https://gist.github.com/hozumi/1472865
(defn sha1 [s]
  (->> (-> "sha1" java.security.MessageDigest/getInstance
           (.digest (.getBytes s)))
       (map #(.substring
              (Integer/toString
               (+ (bit-and % 0xff) 0x100) 16) 1))
       (apply str)))

;; We generated truncated hashes when creating our record IDs.
(defn minihash [s]
  (subs (sha1 s) 0 11))

(defn migrated? []
  (not (zero?
        (sql/with-connection (System/getenv "DATABASE_URL")
          (sql/with-query-results results
            [(str "SELECT count(*) FROM information_schema.tables "
                  "WHERE table_name='records'")]
            (:count (first results)))))))

(defn create-tables []
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/create-table :records
                      [:id "varchar(11)" "NOT NULL"]
                      [:dataset :varchar]
                      [:ec :varchar]
                      [:name :varchar]
                      [:alt_name :varchar]
                      [:source :varchar]
                      [:organ :varchar]
                      [:mw :varchar]
                      [:sub_no :varchar]
                      [:sub_mw :varchar]
                      [:no_iso :varchar]
                      [:pi_max :varchar]
                      [:pi_range_min :varchar]
                      [:pi_range_max :varchar]
                      [:pi_major :varchar]
                      [:pi :varchar]
                      [:temp :varchar]
                      [:method :varchar]
                      [:valid :varchar]
                      [:sequence :varchar]
                      [:species :varchar]
                      [:citations :varchar]
                      [:abstract :varchar]
                      [:pubmed :varchar]
                      [:notes :varchar]
                      [:created_at :timestamp "NOT NULL"
                       "DEFAULT CURRENT_TIMESTAMP"])

    (sql/create-table :users
                      [:id :serial "PRIMARY KEY"]
                      [:email :varchar "NOT NULL"]
                      [:pass :varchar "NOT NULL"])))

(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (create-tables)
    (println " done")))
