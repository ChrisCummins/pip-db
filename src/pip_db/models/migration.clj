(ns pip-db.models.migration
  (:require [clojure.java.jdbc :as sql]))

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
                      [:id :serial "PRIMARY KEY"]
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

    (sql/create-table :user_types
                      [:id :serial "PRIMARY KEY"]
                      [:type_name :varchar "NOT NULL"])

    (sql/create-table :users
                      [:id :serial "PRIMARY KEY"]
                      [:email :varchar "NOT NULL"]
                      [:pass :varchar "NOT NULL"]
                      [:user_type_id :serial "NOT NULL"])))

(defn create-user-type [user-type]
  (sql/with-connection (System/getenv "DATABASE_URL")
    (sql/insert-values :user_types [:type_name] [user-type])))

(defn migrate []
  (when-not (migrated?)
    (print "Creating database structure...") (flush)
    (create-tables)
    (println " done")

    (print "Creating admin user type...") (flush)
    (create-user-type "admin")
    (println " done")))
